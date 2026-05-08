# 🚀 Java RMI (Remote Method Invocation) — End-to-End Summary

> A quick revision guide for implementing Java RMI without rewriting everything from scratch.

---

# 📌 What is RMI?

**RMI (Remote Method Invocation)** allows a Java program running on one machine/JVM to call methods on an object running on another machine/JVM.

It is Java’s implementation of **RPC (Remote Procedure Call)**.

---

# 🧠 Core Idea

Instead of manually using:

- sockets
- streams
- networking protocols

Java RMI lets remote methods look like local method calls.

```java
remoteObject.sayHello("Alice");
```

Even though the object may exist on another machine.

---

# 🏗️ Project Structure

```text
RMIProject/
│
├── MyRemoteInterface.java
├── MyRemoteImpl.java
├── MyRemoteServer.java
└── MyRemoteClient.java
```

---

# 🔥 Complete RMI Workflow

```text
CLIENT
   |
   | invokes method
   v
STUB
   |
   | marshals data
   v
NETWORK
   |
   v
SKELETON
   |
   | invokes real object
   v
REMOTE OBJECT
   |
   | returns result
   v
CLIENT
```

---

# ✅ STEP 1 — Define Remote Interface

## 📄 File:
```text
MyRemoteInterface.java
```

## ✅ Purpose

Defines methods that clients can invoke remotely.

## ✅ Important Rules

- must extend `Remote`
- methods must throw `RemoteException`

## ✅ Code

```java
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyRemoteInterface extends Remote {

    String sayHello(String name)
            throws RemoteException;
}
```

## 🔑 Key Concepts

| Part | Purpose |
|---|---|
| `Remote` | Marks interface as remotely accessible |
| `RemoteException` | Handles network failures |

---

# ✅ STEP 2 — Implement Remote Methods

## 📄 File:
```text
MyRemoteImpl.java
```

## ✅ Purpose

Contains actual business logic.

## ✅ Code

```java
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyRemoteImpl
        extends UnicastRemoteObject
        implements MyRemoteInterface {

    public MyRemoteImpl()
            throws RemoteException {

        super();
    }

    @Override
    public String sayHello(String name)
            throws RemoteException {

        return "Hello, " + name + "!";
    }
}
```

## 🔑 Key Concepts

| Part | Purpose |
|---|---|
| `UnicastRemoteObject` | Exports object for remote access |
| `super()` | Initializes remote object services |
| `implements MyRemoteInterface` | Ensures required remote methods exist |

---

# ✅ STEP 3 — Create RMI Registry

## 📄 Inside:
```text
MyRemoteServer.java
```

## ✅ Purpose

Creates naming service for remote objects.

## ✅ Important Port

```text
1099
```

(default RMI registry port)

## ✅ Code

```java
Registry registry =
        LocateRegistry.createRegistry(1099);
```

## 🔑 Key Concepts

| Component | Role |
|---|---|
| Registry | Stores remote object references |
| Port 1099 | Default RMI port |

---

# ✅ STEP 4 — Register Remote Object

## 📄 Inside:
```text
MyRemoteServer.java
```

## ✅ Purpose

Makes remote object discoverable by clients.

## ✅ Code

```java
MyRemoteImpl remoteImpl =
        new MyRemoteImpl();

registry.bind("MyRemote", remoteImpl);
```

## 🔑 Key Concepts

| Method | Purpose |
|---|---|
| `bind()` | Registers object in registry |
| `"MyRemote"` | Name clients use to find object |

---

# ✅ Full Server Code

```java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MyRemoteServer {

    public static void main(String[] args) {

        try {

            Registry registry =
                    LocateRegistry.createRegistry(1099);

            MyRemoteImpl remoteImpl =
                    new MyRemoteImpl();

            registry.bind("MyRemote", remoteImpl);

            System.out.println(
                "Remote object bound to registry.");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
```

---

# ✅ STEP 5 — Client Looks Up Object

## 📄 File:
```text
MyRemoteClient.java
```

## ✅ Purpose

Finds remote object from registry.

## ✅ Code

```java
MyRemoteInterface remoteObject =
    (MyRemoteInterface)
    Naming.lookup(
    "rmi://localhost/MyRemote");
```

## 🔑 URL Breakdown

| Part | Meaning |
|---|---|
| `rmi://` | RMI protocol |
| `localhost` | Server address |
| `MyRemote` | Registered object name |

---

# ✅ STEP 6 — Client Invokes Remote Methods

## ✅ Code

```java
String result =
        remoteObject.sayHello("Alice");

System.out.println(result);
```

## ✅ Output

```text
Hello, Alice!
```

---

# ✅ Full Client Code

```java
import java.rmi.Naming;

public class MyRemoteClient {

    public static void main(String[] args) {

        try {

            MyRemoteInterface remoteObject =
                    (MyRemoteInterface)
                    Naming.lookup(
                    "rmi://localhost/MyRemote");

            String result =
                    remoteObject.sayHello("Alice");

            System.out.println(result);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
```

---

# ✅ STEP 7 — Stub & Skeleton Handle Communication

## 🧩 Stub (Client Side)

Acts like local proxy object.

### Responsibilities

- marshals parameters
- sends request
- waits for response
- unmarshals result

---

## 🧩 Skeleton (Server Side)

Handles incoming requests.

### Responsibilities

- receives request
- unmarshals data
- invokes real method
- marshals result
- sends response

---

# 🔄 Internal Communication Flow

```text
Client
  |
  | sayHello("Alice")
  v
Stub
  |
  | converts data -> bytes
  v
Network
  |
  v
Skeleton
  |
  | invokes real method
  v
Remote Object
  |
  | returns result
  v
Client
```

---

# ✅ STEP 8 — Result Returned Transparently

The client receives:

```text
Hello, Alice!
```

without handling:

- sockets
- packet transfer
- serialization
- networking

Java RMI hides everything internally.

---

# ⚡ Compile & Run

## 🔨 Compile

```bash
javac *.java
```

---

## ▶️ Run Server

```bash
java MyRemoteServer
```

Expected:

```text
Remote object bound to registry.
```

---

## ▶️ Run Client

```bash
java MyRemoteClient
```

Expected:

```text
Hello, Alice!
```

---

# 🎯 Most Important Exam Concepts

| Concept | Meaning |
|---|---|
| RPC | Remote function calling |
| RMI | Java implementation of RPC |
| Stub | Client-side proxy |
| Skeleton | Server-side proxy |
| Registry | Naming service |
| Remote Interface | Contract between client/server |
| RemoteException | Handles network errors |
| UnicastRemoteObject | Makes object remotely accessible |

---

# 🚀 One-Line Summary

> Java RMI allows remote Java objects to communicate as if they were local objects while automatically handling networking, serialization, and communication behind the scenes.
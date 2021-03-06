Nove - An event bus by Cliqz
============================

Nove is a drop-in replacement for [Square's Otto][1].

Nove generates message dispatching code using an annotation processor 
(nove-compiler). The generated classes are loaded at runtime using reflection:
this approach guarantees two order of magnitude better performance on Android if
compared with Otto.

Nove Bus is self contained, uses only the Java Standard Library and has a small
memory footprint, it does not use any third party library and is not Android
specific.

Nove does not offer threading enforcement as it is delegated to the user.

Usage
-----

Use the ``@Subscribe`` annotation to mark methods that will receive the messages
and ``Bus.post(...)`` to send a message.

```java
public class Example {
    
    public static void main(int argc, String[] argv) {
        final Bus bus = new Bus();
        final Example example = new Example(bus);
        bus.post("Hello Nove!");
    }
    
    Example(Bus bus) {
        bus.register(this);
    }
    
    @Subscribe
    void onReceiveMessage(String msg) {
        System.out.println(msg);
    }
}
```

Any class can be used as a message type but, despite the above example, we
suggest to not use Java library standard classes.

Message type classes should always be final.

```java
final class Message {
    // ...
}
```

Try to avoid using not final message classes or to extend another message type.
In the following example, ``handleTheOtherMessage`` will not be called when we
post a ``Message2`` message.

```java
class Example {
    
    static class Message1 {
        // ...
    }
    
    static class Message2 extends Message1 {
        // ...
    }
    
    @Subscribe
    void handleMessage(Message1 msg) {
        // ...
    }
    
    @Subscribe
    void handleTheOtherMessage(Message2 msg) {
        // ...
    }
}
```

Proguard
--------

TODO

Download
--------

Downloadable .jars can be found on the [release page][2].

In a Maven project, include the `nove` artifact in the dependencies section
of your `pom.xml` and the `nove-compiler` artifact as either an `optional` or
`provided` dependency:

```xml
<dependencies>
  <dependency>
    <groupId>com.cliqz.nove</groupId>
    <artifactId>nove</artifactId>
    <version>0.2.0</version>
  </dependency>
  <dependency>
    <groupId>com.cliqz.nove</groupId>
    <artifactId>nove-compiler</artifactId>
    <version>0.2.0</version>
    <optional>true</optional>
  </dependency>
</dependencies>
```

### Java Gradle
```groovy
// Add plugin https://plugins.gradle.org/plugin/net.ltgt.apt
plugins {
  id "net.ltgt.apt" version "0.5"
}

dependencies {
  compile 'com.cliqz.nove:nove:0.2.0'
  apt 'com.cliqz.nove:nove-compiler:0.2.0'
}
```

### Android Gradle (plugin version 3.+)
```groovy
dependencies {
  implementation 'com.cliqz:nove:0.1.0'
  annotationProcessor 'com.cliqz:nove-compiler:0.1.0'
}
```

## Contributing
* Bug reports, feature requests and general question can be added as an Issue. 
* PRs are welcome. 
* Questions? Concerns? Feel free to [contact us][3].

[1]: https://github.com/square/otto
[2]: https://github.com/cliqz-oss/nove/releases/latest
[3]: mailto:cliqz-oss@cliqz.com?subject=nove

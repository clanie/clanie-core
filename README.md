# clanie-core

Core configuration, utilities and services etc.


## Naming Conventions

### Class Names

**Utils**
:   suffix is for classes with static utility methods.

**Service**
:   suffix is for Spring @Service Beans providing service to be used by other parts of the application.

**Component**
:   suffix is for Components plugged in to Spring infrastructure. These can also have other names reflecting
    what they do or is used for, but they should always have a @Component annotation - not @Service
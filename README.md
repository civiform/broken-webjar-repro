# Java compilation error repro based on Play hello world sample

This repo is forked from [play java hello world](https://github.com/playframework/play-samples/tree/2.8.x/play-java-hello-world-tutorial) to reproduce a compilation error when using webjars.

## Reproduction steps

1. make sure you are using OpenJDK 11.0.14.x
2. clone this repo
3. run `sbt` then `compile`

or if you don't have sbt installed locally you can run it in docker:

1. `docker build -f Dockerfile -t broken-webjar-repro .`
2. `docker run -it --rm broken-webjar-repro:latest /bin/bash` then `sbt` and `compile`.

## Bug details

The bug occurs for OpenJDK 11.0.14.x but not 11.0.10.x

The bug appears when including the `"org.webjars.npm" % "azure__storage-blob" % "10.5.0"` library dependency. Including other webjars, including other webjars from azure, do not cause a compilation error.

When `azure__storage-blob` is included, Java presents a compilation error message `ZIP file can't be opened as a file system because an entry has a '.' or '..' element in its name` for every package name component in the project. E.g.

```
[error] /home/bionj/dev/civiform/universal-application-tool-0.0.1/app/auth/AccountNonexistentException.java:1:1: cannot access auth
[error]   ZIP file can't be opened as a file system because an entry has a '.' or '..' element in its name
[error] /home/bionj/dev/civiform/universal-application-tool-0.0.1/app/auth/oidc/AdOidcProvider.java:1:1: cannot access auth.oidc
[error]   ZIP file can't be opened as a file system because an entry has a '.' or '..' element in its name
[error] /home/bionj/dev/civiform/universal-application-tool-0.0.1/app/auth/saml/InvalidSamlProfileException.java:1:1: cannot access auth.saml
[error]   ZIP file can't be opened as a file system because an entry has a '.' or '..' element in its name
```

The error message looks like it is coming from a bugfix [introduced to OpenJDK here](https://bugs.openjdk.java.net/browse/JDK-8274727).

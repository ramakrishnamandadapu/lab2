grpc Lab2 Assignment CMPE 272 SJSU
==============================================

In order to run the examples simply execute one of the gradle tasks `pollSeviceServer`,`pollServiceClient`,
`routeGuideServer`,`routeGuideClient`, `helloWorldServer`, or `helloWorldClient`.

For example, say you want to play around with the Poll Service. First you want to start
the server and then have the client connect to it and let the good times roll.

Assuming you added the grpc-java libray folder to this project and you would first start the Poll Service server
by running

```
$ ./gradlew :grpc-examples:pollServiceServer

```

and in a different terminal window then run the poll service client by typing

```
$ ./gradlew :grpc-examples:pollServiceClient
```

That's it!



package edu.sjsu.cmpe273.lab2;

import io.grpc.ChannelImpl;
import io.grpc.transport.netty.NegotiationType;
import io.grpc.transport.netty.NettyChannelBuilder;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
* A simple client that requests a greeting from the {@link HelloWorldServer}.
*/
public class PollServiceClient {
private static final Logger logger = Logger.getLogger(PollServiceClient.class.getName());
private final ChannelImpl channel;
private final PollServiceGrpc.PollServiceBlockingStub blockingStub;
public PollServiceClient(String host, int port) {
channel =
NettyChannelBuilder.forAddress(host, port).negotiationType(NegotiationType.PLAINTEXT)
.build();
blockingStub = PollServiceGrpc.newBlockingStub(channel);
}
public void shutdown() throws InterruptedException {
channel.shutdown().awaitTerminated(5, TimeUnit.SECONDS);
}
public void createPoll() {
try {
//logger.info("Will try to greet " + name + " ...");
PollRequest request = PollRequest.newBuilder().setModeratorId("1")
 .setQuestion("What type of smartphone do you have?")
.setStartedAt("2015-03-18T13:00:00.000Z")
.setExpiredAt("2015-03-19T13:00:00.000Z")
.addChoice("Android")
.addChoice("iPhone") 
.build();
PollResponse response = blockingStub.createPoll(request);
logger.info("Created a new poll with Poll Id: " + response.getId());
} catch (RuntimeException e) {
logger.log(Level.WARNING, "RPC failed", e);
return;
}
}
public static void main(String[] args) throws Exception {
PollServiceClient client = new PollServiceClient("localhost", 8080);
try {

client.createPoll();
} finally {
client.shutdown();
}
}
}

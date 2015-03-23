package edu.sjsu.cmpe273.lab2;
import io.grpc.ServerImpl;
import io.grpc.transport.netty.NettyServerBuilder;
import java.util.logging.Logger;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;
/**
* Server that manages startup/shutdown of a {@code Greeter} server.
*/
public class PollServiceServer {
private static final Logger logger = Logger.getLogger(PollServiceServer.class.getName());
/* The port on which the server should run */
private int port = 8080;
private ServerImpl server;
private SecureRandom random=new SecureRandom();
private void start() throws Exception {
server = NettyServerBuilder.forPort(port)
.addService(PollServiceGrpc.bindService(new PollServiceImpl()))
.build().start();
logger.info("Server started, listening on " + port);
Runtime.getRuntime().addShutdownHook(new Thread() {
@Override
public void run() {
// Use stderr here since the logger may have been reset by its JVM shutdown hook.
System.err.println("*** shutting down gRPC server since JVM is shutting down");
PollServiceServer.this.stop();
System.err.println("*** server shut down");
}
});
}
private void stop() {
if (server != null) {
server.shutdown();
}
}
/**
* Main launches the server from the command line.
*/
public static void main(String[] args) throws Exception {
final PollServiceServer server = new PollServiceServer();
server.start();
}
private class PollServiceImpl implements PollServiceGrpc.PollService {
private List<PollRequest> pollList=new ArrayList<PollRequest>();
@Override
public void createPoll(PollRequest req, io.grpc.stub.StreamObserver<PollResponse> responseObserver) {
pollList.add(req);
logger.info("Server is creating a Poll for Moderator::"+req.getModeratorId());
PollResponse reply = PollResponse.newBuilder().setId( new BigInteger(30, random).toString(36).toUpperCase()).build();
responseObserver.onValue(reply);
responseObserver.onCompleted();
}
}
}

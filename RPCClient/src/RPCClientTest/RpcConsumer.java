package RPCClientTest;

public class RpcConsumer {
	 public static void main(String[] args) throws Exception {  
	        HelloService service = RPCClientFramework.refer(HelloService.class, "127.0.0.1", 1234);  
	        for (int i = 0; i < Integer.MAX_VALUE; i ++) {  
	            String hello = service.sayhello("yukun" + i);  
	            System.out.println(hello);  
	            Thread.sleep(1000);  
	        }  
	    }  
}

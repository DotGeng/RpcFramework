package RPCTestPackage;

public class RpcProvider {
	public static void main(String[] args) throws Exception {
		HelloService service = new HelloServiceImpl();  
        RpcServerFrameWork.export(service, 1234);
	}
}

package RPCTestPackage;

public class HelloServiceImpl implements HelloService{

	@Override
	public String sayhello(String  name) {
		return "hello" + name ;
	}
	
}

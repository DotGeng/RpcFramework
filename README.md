这是一款Rpc框架的实现，涉及到的技术有socket通信，动态代理技术，反射技术，多线程技术。希望兴趣的同仁可以进行优化或者改进。

这个框架一共包括两部分：
1，文件夹RPCClient是客户端运行框架；
2，文件夹RpcServer是服务器端运行框架；
RPCClient:
	类RPCClientFramework是框架在在客户端的主要实现类；
	类RpcConsumer，这个里边可以可以实现客户端对服务器端的方法调用。
	接口helloService是一套服务器端的一套业务接口，这个文件在客户端和服务器端各有一份；
RpcServer:
	类RpcServerFramework是框架在服务器端主要的实现类；
	类RpcProvider是服务器框架的启动类；
	接口HelloService是服务器端的一套业务接口；这个文件在客户端和服务器端各有一份；
	类HelloServiceImpl是服务器对HelloService接口的实现类；
	
如何使用？
	 （1）把RpcServer作为服务器端，在RpcProvider类中启动main函数，在HelloService实现自己的业务接口；在HelloServiceImpl实现自己的业务方法；
	 （2）启动类RpcProvider指定端口号，并启动main函数；
	 （3）在RPCClient中的HelloService接口中添加在服务器端实现的接口；
	 （4）在PRCConsumer中，指定服务器的ip地址，端口号后，调用相应的接口即可；
	

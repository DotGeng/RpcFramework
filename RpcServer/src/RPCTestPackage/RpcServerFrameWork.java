package RPCTestPackage;


import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.lang.reflect.InvocationHandler;  
import java.lang.reflect.Method;  
import java.lang.reflect.Proxy;  
import java.net.ServerSocket;  
import java.net.Socket; 

public class RpcServerFrameWork {  
	  
	 /** 
     * 暴露服务 
     *  
     * @param service 服务实现 
     * @param port 服务端口 
     * @throws Exception 
     */  
    public static void export(final Object service, int port) throws Exception {  
        if (service == null)  
            throw new IllegalArgumentException("service instance == null");  
        if (port <= 0 || port > 65535)  
            throw new IllegalArgumentException("Invalid port " + port);  
        System.out.println("Export service " + service.getClass().getName() + " on port " + port);  
        ServerSocket server = new ServerSocket(port);  
        for(;;) {  
            try {  
                final Socket socket = server.accept();//服务器端一旦收到消息，就创建一个线程进行处理  
                new Thread(new Runnable() {  
                    public void run() {  
                        try {  
                            try {  
                                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                                try {  
                                    String methodName = input.readUTF();//service是服务器端提供服务的对象，但是，要通过获取到的调用方法的名称，参数类型，以及参数来选择对象的方法，并调用。获得方法的名称  
                                    Class<?>[] parameterTypes = (Class<?>[])input.readObject();//获得参数的类型  
                                    Object[] arguments = (Object[])input.readObject();//获得参数  
                                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());  
                                    try {  
                                        Method method = service.getClass().getMethod(methodName, parameterTypes);//通过反射机制获得方法  
                                        Object result = method.invoke(service, arguments);//通过反射机制获得类的方法，并调用这个方法  
                                        output.writeObject(result);//将结果发送  
                                    } catch (Throwable t) {  
                                        output.writeObject(t);  
                                    } finally {  
                                        output.close();  
                                    }  
                                } finally {  
                                    input.close();  
                                }  
                            } finally {  
                                socket.close();  
                            }  
                        } catch (Exception e) {  
                            e.printStackTrace();  
                        }  
                    }  
                }).start();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    
}  

package RPCClientTest;

import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.lang.reflect.InvocationHandler;  
import java.lang.reflect.Method;  
import java.lang.reflect.Proxy;  
import java.net.ServerSocket;  
import java.net.Socket; 

public class RPCClientFramework {  
    /** 
     * 引用服务 
     *  
     * @param <T> 接口泛型 
     * @param interfaceClass 接口类型 
     * @param host 服务器主机名 
     * @param port 服务器端口 
     * @return 远程服务 
     * @throws Exception 
     *///原理是通过代理，获得服务器端接口的一个“代理”的对象。对这个对象的所有操作都会调用invoke函数，在invoke函数中，是将被调用的函数名，参数列表和参数发送到服务器，并接收服务器处理的结果  
    @SuppressWarnings("unchecked")  
    public static <T> T refer(final Class<T> interfaceClass, final String host, final int port) throws Exception {  
        if (interfaceClass == null)  
            throw new IllegalArgumentException("Interface class == null");  
        if (! interfaceClass.isInterface())  
            throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");  
        if (host == null || host.length() == 0)  
            throw new IllegalArgumentException("Host == null!");  
        if (port <= 0 || port > 65535)  
            throw new IllegalArgumentException("Invalid port " + port);  
        System.out.println("Get remote service " + interfaceClass.getName() + " from server " + host + ":" + port);  
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, new InvocationHandler() {  
            public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {  
                Socket socket = new Socket(host, port);  
                
                try {  
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());  
                    try {  
                        output.writeUTF(method.getName());  
                        output.writeObject(method.getParameterTypes());  
                        output.writeObject(arguments);  
                        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());  
                        try {  
                            Object result = input.readObject();  
                            if (result instanceof Throwable) {  
                                throw (Throwable) result;  
                            }  
                            return result;  
                        } finally {  
                            input.close();  
                        }  
                    } finally {  
                        output.close();  
                    }  
                } finally {  
                    socket.close();  
                }  
            }  
        });  
    }  
  
}  

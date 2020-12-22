/*
* 后续可以添加：注销功能，返回到注册界面
* */
public class Management {
    Server s;
    Client c;
    regJFrame register;
    logJFrame login1, login2;
    public static void main(String[] args) {
        new Management();
    }

    public Management() {
        register = new regJFrame();
        login1 = new logJFrame();
        login2 = new logJFrame();
        int cnt = 0;
        while(!login1.successful_login || !login2.successful_login) {
            //若直接放空循环则无法break，不知道为什么
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        s = new Server();
        c = new Client();


        Thread server_thread = new Thread(s);
        Thread client_thread = new Thread(c);
        server_thread.start();
        client_thread.start();
    }

}

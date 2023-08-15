package com.example.mandroid.connectionpool;

import java.util.ArrayList;
import java.util.List;

public class ConnectionPoll {
    private final List<DatabaseConnection> connections;
    int maxConnections;

    public ConnectionPoll(int maxConnections) {
        connections = new ArrayList();
        this.maxConnections = maxConnections;
        initializeConnections();
    }

    /**
     * 构造方法添加连接对象到连接池中
     */
    private void initializeConnections() {
        for (int i = 0; i < maxConnections; i++) {
            connections.add(new DatabaseConnection());
        }
    }

    /**
     * 如果连接池不为空,取出第一个连接对象
     * @return
     */
    public DatabaseConnection getConnection(){
        DatabaseConnection connection = null;
        synchronized(connections){
            if (!connections.isEmpty()){
                connection = connections.remove(0);
            }
        }
        return connection;
    }

    public void releaseConnection(DatabaseConnection connection){
        if (connection != null){
            synchronized (connections){
                connections.add(connection);
            }
        }
    }
}

package database

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.hibernate.cfg.Configuration
import org.hibernate.service.ServiceRegistryBuilder

object DBTool {
    lateinit var sessionFactory : SessionFactory
    lateinit var session : Session
    lateinit var transaction : Transaction

    fun init() {
        val config = Configuration().configure("hiberate.cfg.xml");
        val serviceRegistry = ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
        sessionFactory = config.buildSessionFactory(serviceRegistry);
        session = sessionFactory.openSession();
        transaction = session.beginTransaction()
    }

    fun destroy() {
        try {
            transaction.commit()
            session.close()
            sessionFactory.close()
        } catch ( e: Exception ) {
            println( e.message )
        }
    }

    fun<T> withTestDao( f: () -> T ) {
        DBTool.init()
        f.invoke()
        DBTool.destroy()
    }
}
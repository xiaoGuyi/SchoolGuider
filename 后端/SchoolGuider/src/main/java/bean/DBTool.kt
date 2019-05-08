package bean

import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.hibernate.cfg.Configuration
import org.hibernate.service.ServiceRegistryBuilder
import java.util.*

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

    fun save( o: Any ): Any {
        init()
        session.save( o )
        destroy()
        return o
    }

    fun save( o: UserRecords ): Long {
        init()
        session.save( o )
        destroy()
        return o.id
    }

    fun<T> withTestDao( f: () -> T ) {
        DBTool.init()
        f.invoke()
        DBTool.destroy()
    }


}
fun main( args: Array<String> ) {
    DBTool.init()
    val user = User()
    user.openId = "zst"
    DBTool.session.save( user)

    DBTool.destroy()

    println( user.id )
}
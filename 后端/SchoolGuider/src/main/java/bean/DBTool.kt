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

    fun update(o: Any){
        init()
        var old: Any
        if(o is Scenery_ZH_Bean){
            old = session.get(Scenery_ZH_Bean::class.java, o.getCid()) as Scenery_ZH_Bean
            old.setIntroduce(o.getIntroduce())
            old.setVoiceName(o.getVoiceName())
            session.update(old)
        }else if(o is Scenery_EN_Bean){
            old = session.get(Scenery_EN_Bean::class.java, o.getCid()) as Scenery_EN_Bean
            old.setIntroduce(o.getIntroduce())
            old.setVoiceName(o.getVoiceName())
            session.update(old)
        }else if(o is Scenery_JA_Bean){
            old = session.get(Scenery_JA_Bean::class.java, o.getCid()) as Scenery_JA_Bean
            old.setIntroduce(o.getIntroduce())
            old.setVoiceName(o.getVoiceName())
            session.update(old)
        }
        destroy()
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

    println("here"+ user.id )
}

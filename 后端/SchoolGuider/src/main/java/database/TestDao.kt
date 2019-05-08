package database

import org.hibernate.Session

object TestDao {

    fun insert( testBean: TestBean ) {
        DBTool.session.save( testBean )
    }
}
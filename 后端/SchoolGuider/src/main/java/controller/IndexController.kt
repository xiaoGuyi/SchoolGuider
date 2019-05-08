package controller

import database.DBTool
import database.TestBean
import database.TestDao
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping( "/")
class IndexController {
    @RequestMapping( "/test" )
    fun test() : String {
        DBTool.withTestDao { ->
            TestDao.insert( with( TestBean.getDefault() ) {
                this.url = "url"
                this
            })
        }
        return "test";
    }
}
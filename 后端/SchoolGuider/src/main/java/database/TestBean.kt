package database

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "test")
data class TestBean(
        @Id
        @GeneratedValue( strategy = GenerationType.AUTO )
        var id: Int,
        var url: String
) {
    companion object {
        fun getDefault() = TestBean( 0, "" )
    }
}
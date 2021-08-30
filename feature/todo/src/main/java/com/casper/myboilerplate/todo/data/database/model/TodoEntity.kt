package com.casper.myboilerplate.todo.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.casper.myboilerplate.todo.domain.model.Todo

@Entity(tableName = "todo")
//@TypeConverters(AlbumImageEntityTypeConverter::class)
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val desc: String,
)

internal fun TodoEntity.toDomainModel() =
    Todo(this.id, this.title, this.desc)

internal fun Todo.toEntity(): TodoEntity {
    return this.id?.let { id ->
        TodoEntity(id = id, title = this.title, desc = this.desc)
    } ?: run {
        TodoEntity(title = this.title, desc = this.desc)
    }
}

/*
internal class AlbumImageEntityTypeConverter {
    private val type = Types.newParameterizedType(List::class.java, AlbumImageEntity::class.java)
    private val adapter: JsonAdapter<List<AlbumImageEntity>> = Moshi.Builder().build().adapter(type)

    @TypeConverter
    fun stringToList(data: String?) =
        data?.let { adapter.fromJson(it) } ?: listOf()

    @TypeConverter
    fun listToString(someObjects: List<AlbumImageEntity>): String =
        adapter.toJson(someObjects)
}
*/

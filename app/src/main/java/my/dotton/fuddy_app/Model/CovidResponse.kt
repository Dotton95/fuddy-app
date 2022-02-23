package my.dotton.fuddy_app.Model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


@Xml(name="response")
data class CovidResponse(
    @Element
    val header: Header,
    @Element
    val body: Body
)

@Xml(name="header")
data class Header(
    @PropertyElement
    val resultCode:String,
    @PropertyElement
    val resultMsg:String
)
@Xml(name="body")
data class Body(
    @Element
    val items:Items,
    @PropertyElement
    val numOfRows:Int,
    @PropertyElement
    val pageNo:Int,
    @PropertyElement
    val totalCount:Int
)

@Xml(name="items")
data class Items(
    @Element
    val item:List<Item>
)

@Xml(name="item")
data class Item(
    @PropertyElement
    val seq:String,
    @PropertyElement
    val stateDt:String
)
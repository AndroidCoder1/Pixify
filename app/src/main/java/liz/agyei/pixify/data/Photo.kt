package liz.agyei.pixify.data

data class Photo (val id: String, val title: String, var url: String = ""){

    public fun setURL(url: String){
        this.url = url
    }
}
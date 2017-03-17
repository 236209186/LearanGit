/**
 * Created with JetBrains WebStorm.
 * User: jack
 * Date: 14-2-17
 * Time: 下午2:57
 * To change this template use File | Settings | File Templates.
 */
function ShowTime() //显示时间的方法
{
    document.getElementById('nowDiv').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());//显示服务器时间
}
setInterval("ShowTime()", "1000");
$(function(){
    ShowTime();
});

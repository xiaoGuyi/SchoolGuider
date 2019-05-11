// pages/introduce/introduce.js
var WxParse = require('../../wxParse/wxParse.js');
const util = require("../../utils/util.js");
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    windowWidth: '',//窗口宽度
    windowHeight: '',//窗口高度
    containWidth:'',

    scenicName: "",
    scenicIntroduce: "",
    voiceUrl: "",
    imageUrls: [],

    article:"",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;

    wx.request({
      url: util.getRecordUrls[1],
      data: {
      },
      success: function (e) {
        var list = e.data;
        for (var i = 0; i < list.length; ++i) {
          var obj = list[i];
          var id = obj.id;
          if (id == app.globalData.recordId) {
            console.log(obj.imageNameList);
            var voiceName = obj.voiceName;
            var scenicName = obj.scenicName;
            var imageNameList = obj.imageNameList;
            var introduce = obj.introduce;
            that.setData({
              scenicName: scenicName,
              scenicIntroduce: introduce,
              voiceUrl: "http://localhost:8080/school_guider/upload/" + voiceName,
              article:introduce
              // imageUrls: that.toList(imageNameList)
            })
            console.log(that.data.imageUrls);
            break;
          }
        }
      }
    })

    // 获取手机屏幕宽高
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          windowWidth: res.windowWidth,
          windowHeight: res.windowHeight,
          containWidth: res.windowWidth-50
        })
      }
    }),
    wx.setNavigationBarColor({
      frontColor: '#ffffff',
      backgroundColor: '#00b7f3',
    }),
    wx.setNavigationBarTitle({
      title: '景点介绍',
    })
    console.log("here\n"+article+"\nhere")
    // var article = `<section data-role="outer" label="Powered by 135editor.com" style="font-size:16px;"><section data-role="outer" label="Powered by 135editor.com" style="font-size:16px;"><p><img src="http://image.135editor.com/files/users/635/6356574/201905/jpHjR36c_hERv.png" alt="image.png"></p><p style="text-indent:28px"><span style="font-family: 宋体;">体育馆位于我校北侧，高大宏伟，从上方俯视整体呈“</span>U”<span style="font-family: 宋体;">型。体育馆有东侧、南侧、西侧三个门。西侧大门上方的</span>“<span style="font-family: 宋体;">体育馆</span>”<span style="font-family: 宋体;">和奥运五环，南侧墙面上的</span>“<span style="font-family: 宋体;">运动小人</span>”<span style="font-family: 宋体;">标志都为体育馆增添了一抹活力。</span></p><p style="text-indent:28px"><span style="font-family: 宋体;">体育馆共有四层。从东侧大门进入，是南邮学子在各项赛事的获奖统计表，彰显青春风采。往左侧走，有各种各样的活动室，例如：体操房、舞龙房、舞蹈房、武术房、跆拳道房等等。往右侧走，主要是我校体育部办公室以及教师办公室。</span></p><p style="text-indent:28px"><span style="font-family: 宋体;">自二楼起，体育馆构造就发生了一些变化。“</span>U”<span style="font-family: 宋体;">型左侧的篮球与羽毛球馆是整个体育馆中地主要部分，供校运动队和同学使用。三楼是体质测试室，大学生体质测试多在这里进行。篮球场看台也从三楼进入。</span></p><p><img src="http://image.135editor.com/files/users/635/6356574/201905/DMvJKNDP_M5em.png" alt="image.png"></p><p style="text-indent:28px">The tall and magnificent gymnasium is located on the north side of our school, which is&nbsp; an "U" type overlooked from above the whole. It has three entrances, the east entrance, the south entrance and the west entrance. The Chinese characters of “gymnasium”, the Olympic rings above the west entrance and the sports figures on the south side wall add an air of vitality to the gymnasium.</p><p style="text-indent:28px">There are four floors in the gymnasium. Inside the east entrance is the award-winning statistical table of NJUPT’s students have got in various events, which shows the youth style of our school. Plus, we can see a variety of activity rooms from the left, such as gymnastics room, dragon dancing room, dancing room, martial arts room, taekwondo room and so on. On the right side of the gymnasium are mostly the office of sports department of our school and the teacher's offices.</p><p style="text-indent:28px">From the second floor, there are some changes in the structure of the gymnasium. The basketball and badminton hall on the left side of the "U" type is the main part of the whole gymnasium, which is used by school sports teams and students. The third floor is the physique test room, where most college students’ physique tests take are carried out. The basketball field stand entrance is also at the third floor.</p><p><br></p><p><br></p></section></section>`;

    WxParse.wxParse('article', 'html', article, that, 5);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
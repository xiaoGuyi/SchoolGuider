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
    search:"",

    cid:0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    
    wx.request({
      url: util.getRecordUrls[app.globalData.index],
      data: {
        search: ""
      },
      success: function (e) {
        var list = e.data;
        for (var i = 0; i < list.length; ++i) {
          var obj = list[i];
          var cid = obj.cid;
          if (cid == app.globalData.recordId) {
            console.log(obj.imageNameList);
            var voiceName = obj.voiceName;
            var scenicName = obj.scenicName;
            var imageNameList = obj.imageNameList;
            var introduce = obj.introduce;
            var article = '`' + introduce + '`'
            that.setData({
              scenicName: scenicName,
              scenicIntroduce: introduce,
              voiceUrl: "http://localhost:8080/school_guider/upload/" + voiceName,
              article: article
              // imageUrls: that.toList(imageNameList)
            })
            // console.log(that.data.article);
            WxParse.wxParse('article', 'html', article, that, 5);
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
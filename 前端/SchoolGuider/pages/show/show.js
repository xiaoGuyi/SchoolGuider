// pages/show/show.js
const util = require( "../../utils/util.js" );
const app = getApp();

Page({

  // 页面的初始数据
  data: {
    scenicName: "",
    scenicIntroduce: "",
    voiceUrl: "",
    imageUrls: [],
    pageIndex: 0,

    languages: [ "English", "Japanese", "中文" ],
    staticText: [
    [ "The current language: ", "The name of scenic spot: ", "The scenic spot introduction: ", "The voice of scenic spot: ", "play", "The scenic pictures: " ],
    [ "The current language: ", "The name of scenic spot: ", "The scenic spot introduction: ", "The voice of scenic spot: ", "play", "The scenic pictures: " ],
    [ "当前语种：", "景点名称：", "景点介绍：", "景点语音：", "播放语音", "景点图片：" ]
    ]
  },

  toList( str ) {
    // console.log( str );
    var list = str.split( "," );
    // console.log( list.length );
    for( var i = 0; i < list.length-1; ++i ) {
      list[i] = "http://localhost:8080/school_guider/upload/" + list[i];
    }
    return list;
  },

  bindPickerChange: function (e) {
    // this.setData({
    //   pageIndex: e.detail.value
    // })
    wx.navigateBack({
      delta: 1
    })
    wx.navigateTo({
      url: "../show/show?pageIndex=" + (e.detail.value)
    })

  },

  // 生命周期函数--监听页面加载
  onLoad: function (options) {
    var that = this;
    that.setData({
      pageIndex: parseInt( options.pageIndex )
    })
    wx.request({
      url: util.getRecordUrls[1],
      data: {
      },
      success: function( e ) {
        var list = e.data;
        for( var i = 0; i < list.length; ++i ) {
          var obj = list[i];
          var id = obj.id;
          if( id == app.globalData.recordId ) {
            console.log( obj.imageNameList );
            var voiceName = obj.voiceName.split(';')[that.data.pageIndex];
            var scenicName = obj.scenicName.split(';')[that.data.pageIndex];
            var imageNameList = obj.imageNameList.split(';')[that.data.pageIndex];
            var introcude = obj.introcude.split(';')[that.data.pageIndex];
            that.setData({
              scenicName: scenicName,
              scenicIntroduce: introcude,
              voiceUrl: "http://localhost:8080/school_guider/upload/" + voiceName,
              imageUrls: that.toList( imageNameList )
            })
            console.log( that.data.imageUrls );
            break;
          }
        }
      }
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
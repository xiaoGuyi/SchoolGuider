// pages/introduce/introduce.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    windowWidth: '',//窗口宽度
    windowHeight: '',//窗口高度
    containWidth:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    // 获取手机屏幕宽高
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          windowWidth: res.windowWidth,
          windowHeight: res.windowHeight,
          containWidth:res.windowWidth-50
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
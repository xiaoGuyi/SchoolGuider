// pages/scan/scan.js
const app = getApp();

Page({

  // 页面的初始数据
  data: {

  },

  scan() {
    wx.scanCode({
      success: (res) => {
        console.log( res );
        app.globalData.recordId = res.result.split(":")[1];
        wx.navigateTo({
          url: "../Language/Language"
        })
      },
      fail: (res) => {
        wx.showToast({
          title: '扫描失败',
          icon: "none",
          duration: 1000
        })
      },
      complete: (res) => {
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
   onLoad: function (options) {

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
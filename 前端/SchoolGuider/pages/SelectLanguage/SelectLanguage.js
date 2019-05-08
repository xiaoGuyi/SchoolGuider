// pages/SelectLanguage/SelectLanguage.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    languages: ["","English", "Japanese", "中文"],
    pageIndex: 0
  },
  bindPickerChange: function (e) {
    // this.setData({
    //   pageIndex: e.detail.value
    // })
    wx.navigateBack({
      delta: 1
    })
    wx.navigateTo({
      url: "../input/input?pageIndex=" + (e.detail.value-1)
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
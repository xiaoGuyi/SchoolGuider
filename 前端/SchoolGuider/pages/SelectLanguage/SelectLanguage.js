// pages/SelectLanguage/SelectLanguage.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    languages: ["","中文", "English", "日语"],
    mode:"",
    nvabarData: {
      showCapsule: 1, //是否显示左上角图标   1表示显示    0表示不显示
      title: '选择语言', //导航栏 中间的标题
    },

    // 此页面 页面内容距最顶部的距离
    height: app.globalData.height * 2 + 20,
  },
  bindPickerChange: function (e) {
    wx.navigateBack({
      delta: 1
    })
    if(e.detail.value != 0){
      if(this.data.mode=="add")
      {
        wx.navigateTo({
          url: "../input/input?pageIndex=" + (e.detail.value - 1)
        })
      }
      else
      {
        wx.navigateTo({
          url: "../change/change?pageIndex=" + (e.detail.value - 1)
        })
      }
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    console.log(options.mode);
    that.setData({
      mode: options.mode
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
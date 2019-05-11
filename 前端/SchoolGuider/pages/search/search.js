// pages/search/search.js
const util = require("../../utils/util.js");
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    searchData:"",
    keyword:"",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  chooseSearchResultAction: function (e) {
    let that = this
    var index = e.currentTarget.dataset.id;
    var value = that.data.searchData[index].name
    console.log(that.data.searchData[index].id)
    that.setData({
      keyword: value
    })
  },

  /**
   * 输入监听
   */
  
  searchInputAction: function (e) {
    console.log(e)
    let that = this
    let value = e.detail.value

    if (value.length == 0) {
      this.setData({
        searchData: []
      })
      return
    }

    wx.request({
      url: util.getRecordUrls[0],
      method: 'POST',
      header: {
        'content-type': 'application/x-www-form-urlencoded;charset=utf-8', 
      },
      data: {
        "search":value
        },
      success: function (res) {
        var searchData;

        searchData = res.data.map(function (res) {
          return { key: value, name: res.scenicName ,id:res.id}
        })
        that.setData({
          searchData:searchData,
        })

      },
      fail: function (res) { },
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
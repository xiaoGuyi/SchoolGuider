// pages/UploadPic/UploadPic.js
const util = require("../../utils/util.js");
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {

  },
addImage() {
    var that = this
    wx.chooseImage({
      sourceType: [],
      success: function (res) {
        // 上传视频
        var uploadUrl = util.uploadFileUrl;
        wx.uploadFile({
          url: uploadUrl,
          filePath: res.tempFilePaths[0],//图片路径，如tempFilePaths[0]
          name: 'file',
          header: {
            "Content-Type": "multipart/form-data"
          },
          formData: {
            file_name: res.tempFilePaths[0]
          },
          success: function (res) {
            // res为后台存储的图片名
            var obj = JSON.parse(res.data);
            wx.showModal({
              title: '请复制以下内容到浏览器中',
              cancelText: '点击复制',
              confirmText: '返回首页',
              content: util.serverUrl + obj.file_name,
              success(res) {
                if (res.confirm) {
                  wx.navigateBack({})
                }
                else {
                  wx.setClipboardData({
                    data: util.serverUrl + obj.file_name,
                    success(res) {
                      wx.getClipboardData({
                        success(res) { }
                      })
                    }
                  })
                }
              }
            })
          }
        })
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
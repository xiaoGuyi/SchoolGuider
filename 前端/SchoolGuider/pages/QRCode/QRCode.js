// pages/QRCode/QRCode.js
const app = getApp();

const QRCode = require( "../../utils/weapp-qrcode.js" );
import rpx2px from '../../utils/rpx2px.js'
let qrcode;

const qrcodeWidth = rpx2px(300);
const qrcodeHeight = rpx2px(300);


Page({


  data: {
    qrcodeWidth: qrcodeWidth,
    qrcodeHeight: qrcodeHeight,
  },

  back() {
    wx.navigateTo({
      url: '../index/index',
    })
  },

  // 长按保存
  save: function () {
    console.log('save')
    wx.showActionSheet({
      itemList: ['保存图片'],
      success: function (res) {
        console.log(res.tapIndex)
        if (res.tapIndex == 0) {
          qrcode.exportImage(function (path) {
            wx.saveImageToPhotosAlbum({
              filePath: path,
            })
          })
        }
      }
    })
  },

  onLoad: function (options) {
    console.log( app.globalData.recordId )
    qrcode = new QRCode('canvas', {
      // usingIn: this,
      text: "recordId:"+app.globalData.recordId,
      //image: '/images/bg.jpg',
      width: qrcodeWidth,
      height: qrcodeHeight,
      colorDark: "#1CA4FC",
      colorLight: "white",
      correctLevel: QRCode.CorrectLevel.H,
    });
  },

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
const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

const uploadFileUrl = "http://localhost:8080/school_guider/upload/uploadFile";
// const uploadRecordUrl = "http://localhost:8080/school_guider/upload/uploadRecords";
// const uploadRecordCNUrl = "http://localhost:8080/school_guider/upload/uploadRecordsCN";
// const getRecordUrl = "http://localhost:8080/school_guider/record/get_record";
// const getRecordCNUrl = "http://localhost:8080/school_guider/record/get_recordCN";

const uploadRecordUrls = ["http://localhost:8080/school_guider/upload/uploadRecords", "http://localhost:8080/school_guider/upload/uploadRecordsCN"];
const getRecordUrls = ["http://localhost:8080/school_guider/record/get_record", "http://localhost:8080/school_guider/record/get_recordCN"];

module.exports = {
  formatTime: formatTime,
  uploadFileUrl: uploadFileUrl,
  uploadRecordUrls: uploadRecordUrls,
  getRecordUrls: getRecordUrls
}

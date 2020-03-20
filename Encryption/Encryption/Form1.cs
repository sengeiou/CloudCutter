using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using System.Windows.Forms;

namespace Encryption
{
    public partial class MainForm : Form
    {
        public MainForm()
        {
            InitializeComponent();
            Form.CheckForIllegalCrossThreadCalls = false;

        }

        private void BtnDownload_Click(object sender, EventArgs e)
        {
            if (txtAPI.Text == "")
            {
                MessageBox.Show("PLT文件下载地址不能为空");
                return;
            }
            if (txtFileUrl.Text == "")
            {
                MessageBox.Show("文件远程路径不能为空");
                return;
            }
            if (txtPLTLocations.Text == "")
            {
                MessageBox.Show("本地PLT文件地址不能为空");
                return;
            }
            if (System.IO.Directory.Exists(txtPLTLocations.Text)==false)
            {
                MessageBox.Show("本地PLT文件地址无效，没有此目录");
                return;
            }

            ThreadStart ts = new ThreadStart(downloadplt);
            Thread t = new Thread(ts);
            t.Start();

        }

        private void downloadplt()
        {
            btnDownload.Enabled = false;
            btnDownload.Text = "开始下载";

            string jsonstr = datadownload(txtAPI.Text);
            string[] filelist = jsonstr.Split('|');
            btnDownload.Text = string.Format( "下载({0}/{1})",0,filelist.Length);
            int i = 0;
            List<string> lserr = new List<string>();
            foreach (var file in filelist)
            {
                bool downloaderr = download(file);
                if (downloaderr == false)
                {
                    if (string.IsNullOrWhiteSpace(file)==false)
                    {
                        lserr.Add(file);
                    }
                }
                btnDownload.Text = string.Format("下载({0}/{1})",++i, filelist.Length);
            }
            if (lserr.Count > 0)
            {
                MessageBox.Show("下载失败：" + string.Join(",", lserr.ToArray()));
            }
            //btnDownload.Text = "下载plt文件";
            if (MessageBox.Show("提示", "是否立即加载数据？", MessageBoxButtons.YesNo) == DialogResult.Yes)
            {
                BtnLoad_Click(null,null);
            }
            btnDownload.Enabled = true;
        }

        public bool download(string filename)
        {
            string url = txtFileUrl.Text+"/" + filename;
            try
            {
                using (var client = new System.Net.WebClient())
                {
                    client.DownloadFile(url, txtPLTLocations.Text+"\\"+filename);
                }
                return true;
            }
            catch (Exception ex)
            {
                return false;
            }

        }

        public string datadownload(string url)
        {
            try
            {

                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
                request.Method = "GET";
                request.Accept = "text/html, application/xhtml+xml, */*";
                request.ContentType = "application/json";

                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                using (StreamReader reader = new StreamReader(response.GetResponseStream(), Encoding.UTF8))
                {
                    string ret = reader.ReadToEnd();
                   return ret;
                }
            }
            catch (Exception ex)
            {

                return  ex.ToString();
            }
        }

        private void BtnLoad_Click(object sender, EventArgs e)
        {
            DirectoryInfo di = new DirectoryInfo(txtPLTLocations.Text);
            foreach (FileInfo fileinfo in di.GetFiles("*.plt"))
            {
                int index = this.dataGridView1.Rows.Add();
                this.dataGridView1.Rows[index].Cells[0].Value = fileinfo.Name;
                this.dataGridView1.Rows[index].Cells[1].Value = "";
                this.dataGridView1.Rows[index].Cells[2].Value = "等待生成";
                this.dataGridView1.Rows[index].Tag = fileinfo;
            }
        }

        private void BtnEncryption_Click(object sender, EventArgs e)
        {
            if (txtEncrypt.Text == "")
            {
                MessageBox.Show("加密文件保存路径不能为空");
                return;
            }
            if (System.IO.Directory.Exists(txtEncrypt.Text) == false)
            {
                Directory.CreateDirectory(txtEncrypt.Text);
            }
                
            if (txtDesKey.Text.Trim().Length!=8)
            {
                MessageBox.Show("加密秘钥必须等于8位");
                return;
            }


            ThreadStart ts = new ThreadStart(startencrypt);
            Thread t = new Thread(ts);
            t.Start();


        }

        private void startencrypt()
        {
            txtDesKey.Enabled = false;
            txtEncrypt.Enabled = false;
            btnEncryption.Enabled = false;
            btnEncryption.Text = "处理中";
            for (int i = 0; i < this.dataGridView1.Rows.Count; i++)
            {
                FileInfo fileinfo = (FileInfo)this.dataGridView1.Rows[i].Tag;
                string filestr = File.ReadAllText(fileinfo.FullName);
                string desstr = DesEncrypt(filestr,txtDesKey.Text);
                string blt = fileinfo.Name.Replace(".plt", ".blt");
                File.WriteAllText(txtEncrypt.Text + "/" + blt, desstr);
                this.dataGridView1.Rows[i].Cells[1].Value = blt;
                this.dataGridView1.Rows[i].Cells[2].Value = "完成";

                btnEncryption.Text = string.Format("加密({0}/{1})", i+1, this.dataGridView1.Rows.Count);
            }
            btnEncryption.Enabled = true;
            txtDesKey.Enabled = true;
            txtEncrypt.Enabled = true;
            btnEncryption.Text = "重新加密";
        }

        /// <summary>
        /// DES加密字符串
        /// </summary>
        /// <param name="pToEncrypt">待加密的字符串</param>
        /// <param name="sKey">加密密钥,要求为8位</param>
        /// <returns>加密成功返回加密后的字符串，失败返回源串</returns>
        public static string DesEncrypt(string pToEncrypt, string sKey)
        {
            StringBuilder ret = new StringBuilder();
            try
            {
                DESCryptoServiceProvider des = new DESCryptoServiceProvider();
                byte[] inputByteArray = Encoding.Default.GetBytes(pToEncrypt);
                des.Key = ASCIIEncoding.ASCII.GetBytes(sKey);
                des.IV = ASCIIEncoding.ASCII.GetBytes(sKey);
                MemoryStream ms = new MemoryStream();
                CryptoStream cs = new CryptoStream(ms, des.CreateEncryptor(), CryptoStreamMode.Write);
                cs.Write(inputByteArray, 0, inputByteArray.Length);
                cs.FlushFinalBlock();
                foreach (byte b in ms.ToArray())
                {
                    ret.AppendFormat("{0:X2}", b);
                }
                return ret.ToString();
            }
            catch
            {
                return pToEncrypt;
            }
        }

        /// <summary>
        /// DES解密字符串
        /// </summary>
        /// <param name="pToDecrypt">待解密的字符串</param>
        /// <param name="sKey">解密密钥,要求为8位,和加密密钥相同</param>
        /// <returns>解密成功返回解密后的字符串，失败返源串</returns>
        public static string DesDecrypt(string pToDecrypt, string sKey)
        {
            MemoryStream ms = new MemoryStream();
            try
            {
                DESCryptoServiceProvider des = new DESCryptoServiceProvider();
                byte[] inputByteArray = new byte[pToDecrypt.Length / 2];
                for (int x = 0; x < pToDecrypt.Length / 2; x++)
                {
                    int i = (Convert.ToInt32(pToDecrypt.Substring(x * 2, 2), 16));
                    inputByteArray[x] = (byte)i;
                }
                des.Key = ASCIIEncoding.ASCII.GetBytes(sKey);
                des.IV = ASCIIEncoding.ASCII.GetBytes(sKey);
                CryptoStream cs = new CryptoStream(ms, des.CreateDecryptor(), CryptoStreamMode.Write);
                cs.Write(inputByteArray, 0, inputByteArray.Length);
                cs.FlushFinalBlock();
                return System.Text.Encoding.Default.GetString(ms.ToArray());
            }
            catch
            {
                return pToDecrypt;
            }
        }
    }
}

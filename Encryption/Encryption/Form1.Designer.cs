namespace Encryption
{
    partial class MainForm
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要修改
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.btnDownload = new System.Windows.Forms.Button();
            this.txtAPI = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.txtPLTLocations = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.btnLoad = new System.Windows.Forms.Button();
            this.btnEncryption = new System.Windows.Forms.Button();
            this.lvbl = new System.Windows.Forms.Label();
            this.txtEncrypt = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.txtDesKey = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.txtFileUrl = new System.Windows.Forms.TextBox();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.plt = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.blt = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.result = new System.Windows.Forms.DataGridViewTextBoxColumn();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.SuspendLayout();
            // 
            // btnDownload
            // 
            this.btnDownload.Location = new System.Drawing.Point(766, 13);
            this.btnDownload.Name = "btnDownload";
            this.btnDownload.Size = new System.Drawing.Size(133, 23);
            this.btnDownload.TabIndex = 0;
            this.btnDownload.Text = "下载plt文件";
            this.btnDownload.UseVisualStyleBackColor = true;
            this.btnDownload.Click += new System.EventHandler(this.BtnDownload_Click);
            // 
            // txtAPI
            // 
            this.txtAPI.Location = new System.Drawing.Point(124, 14);
            this.txtAPI.Name = "txtAPI";
            this.txtAPI.Size = new System.Drawing.Size(299, 21);
            this.txtAPI.TabIndex = 1;
            this.txtAPI.Text = "http://cmsdev.app-link.org/alucard263096/cloudcutter/api/phone/modelliststr";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(12, 18);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(95, 12);
            this.label1.TabIndex = 2;
            this.label1.Text = "PLT文件下载地址";
            // 
            // txtPLTLocations
            // 
            this.txtPLTLocations.Location = new System.Drawing.Point(124, 42);
            this.txtPLTLocations.Name = "txtPLTLocations";
            this.txtPLTLocations.Size = new System.Drawing.Size(636, 21);
            this.txtPLTLocations.TabIndex = 3;
            this.txtPLTLocations.Text = "./plt";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(12, 45);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(95, 12);
            this.label2.TabIndex = 4;
            this.label2.Text = "本地PLT文件地址";
            // 
            // btnLoad
            // 
            this.btnLoad.Location = new System.Drawing.Point(766, 40);
            this.btnLoad.Name = "btnLoad";
            this.btnLoad.Size = new System.Drawing.Size(133, 23);
            this.btnLoad.TabIndex = 5;
            this.btnLoad.Text = "加载PLT文件列表";
            this.btnLoad.UseVisualStyleBackColor = true;
            this.btnLoad.Click += new System.EventHandler(this.BtnLoad_Click);
            // 
            // btnEncryption
            // 
            this.btnEncryption.Location = new System.Drawing.Point(766, 67);
            this.btnEncryption.Name = "btnEncryption";
            this.btnEncryption.Size = new System.Drawing.Size(133, 23);
            this.btnEncryption.TabIndex = 8;
            this.btnEncryption.Text = "立刻加密";
            this.btnEncryption.UseVisualStyleBackColor = true;
            this.btnEncryption.Click += new System.EventHandler(this.BtnEncryption_Click);
            // 
            // lvbl
            // 
            this.lvbl.AutoSize = true;
            this.lvbl.Location = new System.Drawing.Point(12, 72);
            this.lvbl.Name = "lvbl";
            this.lvbl.Size = new System.Drawing.Size(101, 12);
            this.lvbl.TabIndex = 7;
            this.lvbl.Text = "加密文件保存路径";
            // 
            // txtEncrypt
            // 
            this.txtEncrypt.Location = new System.Drawing.Point(124, 69);
            this.txtEncrypt.Name = "txtEncrypt";
            this.txtEncrypt.Size = new System.Drawing.Size(280, 21);
            this.txtEncrypt.TabIndex = 6;
            this.txtEncrypt.Text = "./pltencrypt";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(410, 72);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(29, 12);
            this.label3.TabIndex = 9;
            this.label3.Text = "秘钥";
            // 
            // txtDesKey
            // 
            this.txtDesKey.Location = new System.Drawing.Point(446, 68);
            this.txtDesKey.Name = "txtDesKey";
            this.txtDesKey.Size = new System.Drawing.Size(314, 21);
            this.txtDesKey.TabIndex = 10;
            this.txtDesKey.Text = "abcd1234";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(431, 18);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(77, 12);
            this.label4.TabIndex = 12;
            this.label4.Text = "文件远程路径";
            // 
            // txtFileUrl
            // 
            this.txtFileUrl.Location = new System.Drawing.Point(514, 14);
            this.txtFileUrl.Name = "txtFileUrl";
            this.txtFileUrl.Size = new System.Drawing.Size(246, 21);
            this.txtFileUrl.TabIndex = 11;
            this.txtFileUrl.Text = "http://applinkupload.oss-cn-shenzhen.aliyuncs.com/alucard263096/cloudcutter/model" +
    "";
            // 
            // dataGridView1
            // 
            this.dataGridView1.AllowUserToAddRows = false;
            this.dataGridView1.AllowUserToDeleteRows = false;
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.plt,
            this.blt,
            this.result});
            this.dataGridView1.Location = new System.Drawing.Point(12, 96);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.ReadOnly = true;
            this.dataGridView1.RowTemplate.Height = 23;
            this.dataGridView1.ShowEditingIcon = false;
            this.dataGridView1.Size = new System.Drawing.Size(886, 447);
            this.dataGridView1.TabIndex = 13;
            // 
            // plt
            // 
            this.plt.HeaderText = "PLT文件";
            this.plt.Name = "plt";
            this.plt.ReadOnly = true;
            this.plt.Width = 200;
            // 
            // blt
            // 
            this.blt.HeaderText = "加密文件";
            this.blt.Name = "blt";
            this.blt.ReadOnly = true;
            this.blt.Width = 200;
            // 
            // result
            // 
            this.result.HeaderText = "结果";
            this.result.Name = "result";
            this.result.ReadOnly = true;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(923, 563);
            this.Controls.Add(this.dataGridView1);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.txtFileUrl);
            this.Controls.Add(this.txtDesKey);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.btnEncryption);
            this.Controls.Add(this.lvbl);
            this.Controls.Add(this.txtEncrypt);
            this.Controls.Add(this.btnLoad);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.txtPLTLocations);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.txtAPI);
            this.Controls.Add(this.btnDownload);
            this.Name = "MainForm";
            this.Text = "plt文件加载地址";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btnDownload;
        private System.Windows.Forms.TextBox txtAPI;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtPLTLocations;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button btnLoad;
        private System.Windows.Forms.Button btnEncryption;
        private System.Windows.Forms.Label lvbl;
        private System.Windows.Forms.TextBox txtEncrypt;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox txtDesKey;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox txtFileUrl;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.DataGridViewTextBoxColumn plt;
        private System.Windows.Forms.DataGridViewTextBoxColumn blt;
        private System.Windows.Forms.DataGridViewTextBoxColumn result;
    }
}


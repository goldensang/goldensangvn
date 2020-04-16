import openpyxl
import requests
from bs4 import BeautifulSoup
from openpyxl.styles import Font
def valid(x):
    for i in range(0, len(x)):
        if x[i] != ' ':
            return 1;
        
    return 0;

def baseN(num, numerals="ABCDEFGHIJKLMNOPQRSTUVWXYZ"):
    kq = "";
    if num == 0:  return 'A';
    while num > 0:
        x =  int(num % 26);
        kq = kq + chr(x + 65);
        num = int(num / 26);
    return kq;

def getCol(index):
    x = baseN(index);
    return x[::-1];
res = dict([]);
start = 19000;
for k in range(0, 0):
    st = str(start + k);
    print(st);
    page = requests.get("http://edusoftweb.hcmiu.edu.vn/default.aspx?page=thoikhoabieu&sta=1&id=ITITIU" + st)
    
    con = page.content.decode("utf-8");
    
    soup = BeautifulSoup(page.content, 'html.parser')
    
        
    data = soup.find(id="ctl00_ContentPlaceHolder1_ctl00_pnlTKB");
    if data is None: continue;
    mssv_html = data.find(id="ctl00_ContentPlaceHolder1_ctl00_lblContentMaSV");
   
    if mssv_html is None: continue;
    mmsv = mssv_html.get_text();
    name_html = data.find(id="ctl00_ContentPlaceHolder1_ctl00_lblContentTenSV");
    after_ = name_html.get_text().split('-');
    
    name = after_[0].strip(' ');
    #asdprint(soup.prettify())
    #--------------Data về lớp----------------
    table = soup.find(class_="grid-roll2");
    element = table.find_all(class_="body-table");
    
    for i in element:
        cnt = 0;
        tmp = i.find_all("td");
        
        for j in tmp:
            cnt += 1;
            x = j.get_text();
            if cnt % 14 == 1:
                x_process = x.split(', ')
                for x_ in x_process:
                    if res.get(x_, 'defaultext') == 'defaultext': res[x_] = [];
                    res[x_].append(name)
                


for i in range(0, 50):
    print(getCol(i))
#f = open("D:\output.txt","w+", encoding = "utf-8");
#f.write(soup.prettify());

#1 = ID khoa
#2 = tên khoa
#5 = mã lớp
#print(soup.prettify());
#for i in l:
#    print(i.get_text(), '\n');

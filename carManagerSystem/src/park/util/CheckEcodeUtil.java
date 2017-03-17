package park.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import park.entity.User;

/**
 * 验证码工具类
 * @author Gux
 *
 */
public class CheckEcodeUtil extends ActionSupport implements SessionAware{
	private InputStream input;  
    private Map<String, Object> session;  
    private String number; 
    private String ecode;
    
	public void getChcekEcode(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			response.setCharacterEncoding("utf-8");
			
			response.setContentType("image/jpeg");
			
			BufferedImage bi=new BufferedImage(60, 20, BufferedImage.TYPE_INT_RGB);
			
			Graphics g=bi.getGraphics();
			
			Random r=new Random();
			g.setColor(new Color(r.nextInt(150),r.nextInt(150),r.nextInt(150)));
		
			g.fillRect(0, 0, 60, 20);
			g.setColor(new Color(r.nextInt(150)+100,r.nextInt(150)+100,r.nextInt(150)+100));
			//int number=r.nextInt(8999)+1000;
			String str=((char)(r.nextInt(26)+'A')+"")+((char)(r.nextInt(26)+'A')+"")+((char)(r.nextInt(26)+'A')+"")+((char)(r.nextInt(26)+'A')+"");
			//g.drawString(String.valueOf(number), 17, 15);
			g.drawString(str, 17, 15);
			HttpSession session=request.getSession();
			session.setAttribute("str", str);
			g.setColor(Color.WHITE);
			g.drawLine(r.nextInt(3)+2, r.nextInt(5), r.nextInt(5)+45, r.nextInt(5));
			//g.drawLine(r.nextInt(3)+2, r.nextInt(10), r.nextInt(10)+45, r.nextInt(10));
			g.drawLine(r.nextInt(3)+2, r.nextInt(15), r.nextInt(15)+45, r.nextInt(15));
			OutputStream os=response.getOutputStream();
			JPEGImageEncoder je=JPEGCodec.createJPEGEncoder(os);
			je.encode(bi);
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
	
	public void checkEcode(){
		if (ecode.length() > 0) {
			PrintWriter printWriter = null; 
			try {
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("utf-8");
				printWriter = response.getWriter();
				String num = (String) session.get("number");  
				if (num.trim().equals(ecode.trim())) {
					printWriter.write("true");
				} else {
					printWriter.write("false");
				}
				printWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();

			} finally{
				if (null != printWriter) {
					printWriter.close();
				}
			}

		}
	}
	
	public String getChcekEcodes() {  
        byte[] buf = this.randomImage();  
        this.input = new ByteArrayInputStream(buf);  
        session.put("number", number);  
        String num = (String) session.get("number");  
        return SUCCESS;  
    }  
  
    private byte[] randomImage() {  
        BufferedImage image =  
  
        new BufferedImage(60, 28, BufferedImage.TYPE_INT_RGB);  
        
        Graphics g = image.getGraphics();  
  
        Random r = new Random();  
        g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));  
        g.fillRect(0, 0, 60, 28);  
        g.setColor(new Color(0, 0, 0));  
  
        number = String.valueOf(r.nextInt(99999));  
        g.drawString(number, 10, 19);  
        g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));  
        g.drawLine(r.nextInt(60), r.nextInt(20), r.nextInt(60), r.nextInt(28));  
        ByteArrayOutputStream os = new ByteArrayOutputStream();  
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);  
        try {  
            encoder.encode(image);  
        } catch (ImageFormatException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return os.toByteArray();  
    }
    
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public InputStream getInput() {
		return input;
	}

	public void setInput(InputStream input) {
		this.input = input;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	
}

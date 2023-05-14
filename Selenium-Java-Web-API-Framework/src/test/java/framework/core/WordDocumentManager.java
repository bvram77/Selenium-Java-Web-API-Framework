package framework.core;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class WordDocumentManager
{
  private final String filePath;
  private final String fileName;
  
  public WordDocumentManager(String filePath, String fileName)
  {
    this.filePath = filePath;
    this.fileName = fileName;
  }
  
  public void createDocument()
  {
    XWPFDocument document = new XWPFDocument();
    
    writeIntoFile(document);
  }
  
  private void writeIntoFile(XWPFDocument document)
  {
    String absoluteFilePath = this.filePath + Util.getFileSeparator() + this.fileName + ".docx";
    FileOutputStream fileOutputStream;
    try
    {
      fileOutputStream = new FileOutputStream(absoluteFilePath);
    }
    catch (FileNotFoundException e)
    {      
      e.printStackTrace();
      throw new FrameworkException("The specified file \"" + absoluteFilePath + "\" does not exist!");
    }
    try
    {      
      document.write(fileOutputStream);
      fileOutputStream.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Error while writing into the specified Word document \"" + absoluteFilePath + "\"");
    }
  }
  
  public void addPicture(File pictureFile)
  {
    CustomXWPFDocument document = openFileForReading();
    XWPFParagraph paragraph = document.createParagraph();
    paragraph.setAlignment(ParagraphAlignment.CENTER);
    
    XWPFRun run = paragraph.createRun();
    run.setText(pictureFile.getName());
    try
    {
      String id = document.addPictureData(new FileInputStream(pictureFile), 
        6);
      
      BufferedImage image = ImageIO.read(pictureFile);
      document.createPicture(id, 
        document.getNextPicNameNumber(6), 
        image.getWidth(), image.getHeight());
    }
    catch (InvalidFormatException|IOException e)
    {
      e.printStackTrace();
      throw new FrameworkException("Exception thrown while adding a picture file to a Word document");
    }
    //String id;
    paragraph = document.createParagraph();
    run = paragraph.createRun();
    run.addBreak(BreakType.PAGE);
    
    writeIntoFile(document);
  }
  
  private CustomXWPFDocument openFileForReading()
  {
    String absoluteFilePath = this.filePath + Util.getFileSeparator() + this.fileName + ".docx";
    FileInputStream fileInputStream;
    try
    {
      fileInputStream = new FileInputStream(absoluteFilePath);
    }
    catch (FileNotFoundException e)
    {      
      e.printStackTrace();
      throw new FrameworkException("The specified file \"" + absoluteFilePath + "\" does not exist!");
    }
    CustomXWPFDocument document;
    try
    {      
      document = new CustomXWPFDocument(fileInputStream);
    }
    catch (IOException e)
    {      
      e.printStackTrace();
      throw new FrameworkException("Error while opening the specified Word document \"" + absoluteFilePath + "\"");
    }    
    return document;
  }
}

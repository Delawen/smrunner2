package smrunner.node;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Tag extends Token
{

    public enum Type {OPEN,CLOSE, OPEN_AND_CLOSE};
    private Type type;
    private Map atributos;
           
    private final static String regexTag = "([\\w]+)([\\s]*([^\\s=]+)[\\s]*=[\\s]*\"([^\"]*)\")*";
    //GRUPOS:                               1       2      3                         4
    private final static Pattern pattern = Pattern.compile(regexTag, /*Pattern.MULTILINE|*/Pattern.CASE_INSENSITIVE);

    public Tag(String text)
    {
        super();
        this.atributos = new HashMap<String, String>();
        this.setContent(text);
    }
    /**
     * Comprueba si dos Text hacen matching.
     * 
     * @param i
     * @return boolean
     */
    @Override
    public boolean match (Item i) 
    {
        //TODO de momento no tienen en cuenta los atributos
        //Si son dos etiquetas del mismo tipo y el mismo contenido
        if(i instanceof Tag)
            if(((Tag)i).getType() == this.type)
                if(((Tag)i).getContent().equals(this.content))
                    return true;
                
        //Si no, no hacen matching:
        return false;
    }
    
        @Override
    public void setContent(String c){
        
        if(c.startsWith("<!") && c.endsWith(">"))
        {
            this.type = Type.OPEN_AND_CLOSE;
            this.content = c.substring(1, c.length() - 1);
        }
        else if(c.startsWith("</") && c.endsWith(">"))
        {
            this.type = Type.CLOSE;
            save(c.substring(2, c.length() - 1));
        }
        else if(c.startsWith("<") && c.endsWith("/>"))
        {
            this.type = Type.OPEN_AND_CLOSE;
            save(c.substring(1, c.length() - 2));
        }
        else if(c.startsWith("<") && c.endsWith(">"))
        {
            this.type = Type.OPEN;
            save(c.substring(1, c.length() - 1));
        }
        else 
            throw new RuntimeException("El contenido no es una etiqueta");
            
    }
        
        
    private void save(String contenido) 
    {
        //TODO no hace bien los atributos porque no se como se recorren bien los grupos
        Matcher matcher=pattern.matcher(contenido);    
        int j = 0;
        while(matcher.find())
        {
//            System.out.println(j + " "+matcher.groupCount()+"  " +matcher.group(1));
//            j++;
            this.content = matcher.group(1);
            
//            startText=matcher.start();
//            endText=matcher.end();
//            fulltext=matcher.group(0);
//            if(matcher.groupCount()>1)
//            {
//                for(int i=1;i<=matcher.groupCount();i++)
//                {
//                    label=matcher.group(i);
//                    startGroup=matcher.start(i);
//                    endGroup=matcher.end(i);
//                    doSometing(label,startGroup,endGroup);
//                }
//            }
        }
        
//        String[] elems = contenido.split(" ");
//
//        this.content = elems[0];
//        
//        for(int i = 1; i < elems.length; i++)
//        {
//            String elem = elems[i];
//            
//            int indice = elem.indexOf("=");
//            if(indice < 0)
//                throw new RuntimeException("El atributo no estaba en formato XHTML.");
//            
//            this.atributos.put(elem.substring(0, indice), elem.substring(indice +1));
//        }
    }
    
    /**
     *
     * 
     * 
     * @param i
     * @return boolean
     */
    @Override
    public boolean equals(Object i)
    {
        if(i instanceof Tag)
            return (((Tag)i).getContent().equals(this.getContent())
                    && this.getType() == ((Tag)i).getType());
        return false;
    }
    
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
    public boolean isOpenTag()
    {
        return Type.OPEN == type || Type.OPEN_AND_CLOSE == type;
    }
    
    public boolean isCloseTag()
    {
        return Type.CLOSE == type || Type.OPEN_AND_CLOSE == type;
    }
    
   
    @Override
    public Object clone() throws CloneNotSupportedException 
    {
        Object o = super.clone();
        
        Map atribClone = new HashMap<String, String>();
        
        Iterator<String> it = this.atributos.keySet().iterator();
        while(it.hasNext())
        {
            String key = it.next();            
            atribClone.put(key.toString(), atributos.get(key).toString());
        }
        ((Tag)o).atributos = atribClone;
        ((Tag)o).type = type;
        
        return o;
    }
    
    @Override
    public String toString()
    {
        String resultado = "<";
        
        if(this.isCloseTag() && !this.isOpenTag())
            resultado += "/";
        
        resultado += this.content;
        
        Iterator<String> it = this.atributos.keySet().iterator();
        while(it.hasNext())
        {
            String key = it.next();
            
            resultado += " " + key + "=" + this.atributos.get(key);
        }
        
        if(this.isCloseTag() && this.isOpenTag())
            resultado += "/>";
        else
            resultado += ">";
        
        return resultado;
    }
    
}

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * Sprite.java
 */

/**
 * Class for a sprite within a Scratch submission.
 *
 * @author Michelle Melton
 * @author Kara Beason
 * @author Cydney Caldwell
 * @version Spr 2017
 */
public class Sprite 
{
    private JSONObject jsonObj;
    private String name;
    private int scriptCount;
    private int variableCount;
    private int listCount;
    private int scriptCommentCount;
    private int soundCount;
    private int costumeCount;
    private int controlBlocksForSprite;
    private int dataBlocksForSprite;
    private int eventsBlocksForSprite;
    private int looksBlocksForSprite;
    private int moreBlocksBlocksForSprite;
    private int motionBlocksForSprite;
    private int operatorsBlocksForSprite;
    private int penBlocksForSprite;
    private int sensingBlocksForSprite;
    private int soundBlocksForSprite;
    private String[] variables;
    private String[] lists;       
    
    /**
     * Sprite constructor.
     *
     * @param jsonObj 
     */
    public Sprite(JSONObject jsonObj)
    {
        this.jsonObj = jsonObj;
        name = FileUtils.getJSONAttribute(jsonObj, "objName");
        scriptCount = FileUtils.getJSONArrayAttribute(jsonObj, 
            "scripts").size();
        variableCount = FileUtils.getJSONArrayAttribute(jsonObj, 
            "variables").size();
        listCount = FileUtils.getJSONArrayAttribute(jsonObj, 
            "lists").size();
        scriptCommentCount = FileUtils.getJSONArrayAttribute(jsonObj, 
            "scriptComments").size();
        soundCount = FileUtils.getJSONArrayAttribute(jsonObj, 
            "sounds").size();
        costumeCount = FileUtils.getJSONArrayAttribute(jsonObj, 
            "costumes").size();
        countBlockCategoriesForSprite();
        populateVariables();
        populateLists();
    }

    /**
     * Get sprite name.
     *
     * @return name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Get script count for sprite.
     *
     * @return count 
     */
    public int getScriptCount()
    {
        return scriptCount;
    }
    
    /**
     * Get variable count for sprite.
     *
     * @return count 
     */
    public int getVariableCount()
    {
        return variableCount;
    }
    
    /**
     * Get list count for sprite.
     *
     * @return count 
     */
    public int getListCount()
    {
        return listCount;
    }
    
    /**
     * Get script comment count for sprite.
     *
     * @return count 
     */
    public int getScriptCommentCount()
    {
        return scriptCommentCount;
    }

    /**
     * Get sound count for sprite.
     *
     * @return count
     */
    public int getSoundCount()
    {
        return soundCount;
    }

    /**
     * Get costume count for sprite.
     *
     * @return count
     */
    public int getCostumeCount()
    {
        return costumeCount;
    }

    /**
     * Get control block count for sprite.
     *
     * @return count
     */
    public int getControlBlocksForSprite()
    {
        return controlBlocksForSprite;
    }
    
    /**
     * Get data block count for sprite.
     *
     * @return count
     */
    public int getDataBlocksForSprite()
    {
        return dataBlocksForSprite;
    }
    
    /**
     * Get events block count for sprite.
     *
     * @return count
     */
    public int getEventsBlocksForSprite()
    {
        return eventsBlocksForSprite;
    }
    
    /**
     * Get looks block count for sprite.
     *
     * @return count
     */
    public int getLooksBlocksForSprite()
    {
        return looksBlocksForSprite;
    }
    
    /**
     * Get more blocks block count for sprite.
     *
     * @return count
     */
    public int getMoreBlocksBlocksForSprite()
    {
        return moreBlocksBlocksForSprite;
    }
    
    /**
     * Get motion block count for sprite.
     *
     * @return count
     */
    public int getMotionBlocksForSprite()
    {
        return motionBlocksForSprite;
    }
    
    /**
     * Get operators block count for sprite.
     *
     * @return count
     */
    public int getOperatorsBlocksForSprite()
    {
        return operatorsBlocksForSprite;
    }
    
    /**
     * Get pen block count for sprite.
     *
     * @return count
     */
    public int getPenBlocksForSprite()
    {
        return penBlocksForSprite;
    }
    
    /**
     * Get sensing block count for sprite.
     *
     * @return count
     */
    public int getSensingBlocksForSprite()
    {
        return sensingBlocksForSprite;
    }
    
    /**
     * Get sound block count for sprite.
     *
     * @return count
     */
    public int getSoundBlocksForSprite()
    {
        return soundBlocksForSprite;
    }

    /**
     * Count block categories for sprite.
     */
    private void countBlockCategoriesForSprite()
    {
        controlBlocksForSprite = 0;
        dataBlocksForSprite = 0;
        eventsBlocksForSprite = 0;
        looksBlocksForSprite = 0;
        moreBlocksBlocksForSprite = 0;
        motionBlocksForSprite = 0;
        operatorsBlocksForSprite = 0;
        penBlocksForSprite = 0;
        sensingBlocksForSprite = 0;
        soundBlocksForSprite = 0;
        JSONArray scripts = FileUtils.getJSONArrayAttribute(jsonObj, "scripts");
        processScripts(scripts);
    }

    /**
     * Process scripts to count blocks by category.
     *
     * @param array 
     */
    private void processScripts(JSONArray array)
    {
        if (array == null || array.size() == 0)
        {
            return;
        }
        
        // If first element is a String, it is the block name.
        // Get and count its category.
        if (array.get(0) instanceof String)
        {
            String category = Submission.getCategory((String) array.get(0));
            if (category != null)
            {
                switch (category)
                {
                    case "control":
                        controlBlocksForSprite++;
                        break;
                    case "data":
                        dataBlocksForSprite++;
                        break;
                    case "events":
                        eventsBlocksForSprite++;
                        break;
                    case "looks":
                        looksBlocksForSprite++;
                        break;
                    case "more blocks":
                        moreBlocksBlocksForSprite++;
                        break;
                    case "motion":
                        motionBlocksForSprite++;
                        break;
                    case "operators":
                        operatorsBlocksForSprite++;
                        break;
                    case "pen":
                        penBlocksForSprite++;
                        break;
                    case "sensing":
                        sensingBlocksForSprite++;
                        break;
                    case "sound":
                        soundBlocksForSprite++;
                        break;
                    default:
                        break;
                }
            }
        }

        // Check for additional array elements, which represent embedded blocks.
        for (int i = 0; i < array.size(); i++)
        {
            if (array.get(i) instanceof JSONArray)
            {
                processScripts((JSONArray) array.get(i));
            }
        }

        return;
    }

    /**
     * Get list of Variables.
     *
     * @return variables
     */
    public String[] getVariables()
    {
        return variables;
    }

    /**
     * Get list of list names.
     *
     * @return array of lists.
     */
    public String[] getLists()
    {
        return lists;
    }

    /**
     * Populate list of variables.
     */
    private void populateVariables()
    {
        JSONArray vars = 
            FileUtils.getJSONArrayAttribute(jsonObj, "variables");
        variables = new String[vars.size()];
        JSONObject children = new JSONObject();
        for (int i = 0; i < vars.size(); i++)
        {
            children = (JSONObject) vars.get(i);
            variables[i] = 
                FileUtils.getJSONAttribute(children, "name");;
        }
    }

    /**
     * Populate lists.
     */
    private void populateLists()
    {
        JSONArray listArray =
            FileUtils.getJSONArrayAttribute(jsonObj, "lists");
        lists = new String[listArray.size()];
        JSONObject children = new JSONObject();
        for (int i = 0; i < listArray.size(); i++)
        {
            children = (JSONObject) listArray.get(i);
            lists[i] =
                FileUtils.getJSONAttribute(children, "listName");
        }
    }

    /**
     * Get variable usage count.
     *
     * @param var - the variable being counted
     * @return the number of times the variable is used
     */
    public int getVariableUsageCount(String var)
    {
        int count = 0;
        JSONArray scripts =
            FileUtils.getJSONArrayAttribute(jsonObj, "scripts");
        String spriteScript = scripts.toString();
        int pos = spriteScript.indexOf(var);
        while (pos >= 0)
        {
            pos += 1;
            count += 1;
            pos = spriteScript.indexOf(var, pos);
        }
        return count;
    }

    /**
     * Get list usage count.
     *
     * @param list - the list being counted
     * @return the number of times the list is used
     */
    public int getListUsageCount(String list)
    {
        int count = 0;
        JSONArray scripts =
            FileUtils.getJSONArrayAttribute(jsonObj, "scripts");
        String spriteScript = scripts.toString();
        int pos = spriteScript.indexOf(list);
        while (pos >= 0)
        {
            pos += 1;
            count += 1;
            pos = spriteScript.indexOf(list, pos);
        }
        return count;
    }
}

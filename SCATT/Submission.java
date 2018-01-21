import java.io.File;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.HashMap;

/**
 * Submission.java
 */

/**
 * Class for a Scratch submission.
 *
 * @author Michelle Melton
 * @author Kara Beason
 * @author Cydney Caldwell
 * @version Spr 2017
 */
public class Submission
{
    private static HashMap<String, String> categoryMap;
    private File sb2;
    private File zipsDir;
    private File unzipsDir;
    private File json;
    private JSONObject jsonObj;
    private Sprite[] sprites;
    private int controlBlocksForStage;
    private int dataBlocksForStage;
    private int eventsBlocksForStage;
    private int looksBlocksForStage;
    private int moreBlocksBlocksForStage;
    private int motionBlocksForStage;
    private int operatorsBlocksForStage;
    private int penBlocksForStage;
    private int sensingBlocksForStage;
    private int soundBlocksForStage;
    private String[] globalVariables;
    private String[] globalLists;
    
    /**
     * Submission constructor.
     *
     * @param sb2  
     */
    public Submission(File sb2)
    {
        this.sb2 = sb2;
        zipsDir = new File("zips");
        unzipsDir = new File("unzips");
        convertToZip();
        unZip();
        parseJSONFile();
        categoryMap = new HashMap<String, String>();
        addControlCategoryMap();
        addDataCategoryMap();
        addEventsCategoryMap();
        addLooksCategoryMap();
        addMoreBlocksCategoryMap();
        addMotionCategoryMap();
        addOperatorsCategoryMap();
        addPenCategoryMap();
        addSensingCategoryMap();
        addSoundCategoryMap();
        countBlockCategoriesForStage();
        createSprites();
        populateGlobalVariables();
        populateGlobalLists();
    }

    /**
     * Get filename of submission.
     *
     * @return filename
     */
    public String getName()
    {
        return sb2.getName();
    }

    /**
     * Check if valid .sb2.
     *
     * @return true if valid .sb2
     */
    public boolean isValid()
    {
        String sb2Name = sb2.getName();
        int len = sb2Name.length();
        String ext = sb2Name.substring(len - 4);
        return ext.equals(".sb2") && sb2.isFile();
    }

    /**
     * Convert .sb2 to .zip.
     * Handles valid .sb2 test internally.
     */
    private void convertToZip()
    {
        if (isValid())
        {
            FileUtils.convertToZip(zipsDir, sb2);
        }
    }

    /**
     * Unzip file. 
     * Handles valid .sb2 test internally.
     */
    private void unZip()
    {
        if (isValid())
        {
            FileUtils.unZip(zipsDir, unzipsDir, sb2);
            String zipDir = FileUtils.getBaseName(sb2);
            json = new File(unzipsDir + File.separator + zipDir 
                + File.separator, "project.json");
        }
    }

    /**
     * Get JSON object of submission.
     *
     * @return jsonObj
     */
    public JSONObject getJSONObject()
    {
        return jsonObj;
    }

    /**
     * Parse JSON file.
     */
    private void parseJSONFile()
    {
        if (json != null)
        {
            jsonObj = FileUtils.parseJSONFile(json.getAbsolutePath());
        }
    }

    /**
     * Get sprite count.
     *
     * @return spriteCount 
     */
    public int getSpriteCount()
    {
        JSONObject obj = FileUtils.getJSONObject(jsonObj, "info");
        return (int) FileUtils.getJSONLongAttribute(obj, "spriteCount");
    }
    
    /**
     * Get script count.
     *
     * @return scriptCount 
     */
    public int getScriptCount()
    {
        JSONObject obj = FileUtils.getJSONObject(jsonObj, "info");
        return (int) FileUtils.getJSONLongAttribute(obj, "scriptCount");
    }

    /**
     * Get script count for stage.
     *
     * @return count 
     */
    public int getScriptCountForStage()
    {
        return getCountForStage("scripts");
    }

    /**
     * Get variable count for stage.
     *
     * @return count 
     */
    public int getVariableCountForStage()
    {
        return getCountForStage("variables");
    }

    /**
     * Get list count for stage.
     *
     * @return count 
     */
    public int getListCountForStage()
    {
        return getCountForStage("lists");
    }
    
    /**
     * Get script comment count for stage.
     *
     * @return count 
     */
    public int getScriptCommentCountForStage()
    {
        return getCountForStage("scriptComments");
    }

    /**
     * Get sound count for stage.
     *
     * @return count 
     */
    public int getSoundCountForStage()
    {
        return getCountForStage("sounds");
    }

    /**
     * Get costume count for stage.
     *
     * @return count 
     */
    public int getCostumeCountForStage()
    {
        return getCountForStage("costumes");
    }
    
    /**
     * Helper method for all CountForStage methods.
     * Pass in JSON attribute name.
     * Get count of specified attribute for stage.
     * 
     * @param attribute  
     * @return count 
     */
    private int getCountForStage(String attribute)
    {
        JSONArray items = 
            FileUtils.getJSONArrayAttribute(jsonObj, attribute);
        return (int) items.size();
    }

    /**
     * Create sprite objects.
     * Unchecked warnings are suppressed because JSONArray does not
     *  allow for a type specification, and this is a private
     *  method only called from within this class.
     */
    @SuppressWarnings("unchecked")
    private void createSprites()
    {
        if (getSpriteCount() > 0)
        {
            JSONArray children = 
                FileUtils.getJSONArrayAttribute(jsonObj, "children");
            sprites = new Sprite[getSpriteCount()];
            
            int j = 0;
            for (int i = 0; i < children.size(); i++)
            {
                if (FileUtils.getJSONAttribute(
                    (JSONObject) children.get(i), "objName") != "")
                {
                    sprites[j] = new Sprite((JSONObject) children.get(i));
                    j++;
                }
            }
        }
    }

    /**
     * Get sprites.
     *
     * @return sprites
     */
    public Sprite[] getSprites()
    {
        return sprites;
    }

    /**
     * Count block categories for stage.
     */
    private void countBlockCategoriesForStage()
    {
        controlBlocksForStage = 0;
        dataBlocksForStage = 0;
        eventsBlocksForStage = 0;
        looksBlocksForStage = 0;
        moreBlocksBlocksForStage = 0;
        motionBlocksForStage = 0;
        operatorsBlocksForStage = 0;
        penBlocksForStage = 0;
        sensingBlocksForStage = 0;
        soundBlocksForStage = 0;
        JSONArray scripts = FileUtils.getJSONArrayAttribute(jsonObj, "scripts");
        processScripts(scripts);
    }

    /**
     * Process scripts to count blocks by category.
     *
     * @param scripts  
     */
    private void processScripts(JSONArray scripts)
    {
        if (scripts.size() == 0)
        {
            return;
        }
        
        // If first element is a String, it is the block name.
        // Get and count its category.
        if (scripts.get(0) instanceof String)
        {
            String category = getCategory((String) scripts.get(0));
            if (category != null)
            {
                switch (category)
                {
                    case "control":
                        controlBlocksForStage++;
                        break;
                    case "data":
                        dataBlocksForStage++;
                        break;
                    case "events":
                        eventsBlocksForStage++;
                        break;
                    case "looks":
                        looksBlocksForStage++;
                        break;
                    case "more blocks":
                        moreBlocksBlocksForStage++;
                        break;
                    case "motion":
                        motionBlocksForStage++;
                        break;
                    case "operators":
                        operatorsBlocksForStage++;
                        break;
                    case "pen":
                        penBlocksForStage++;
                        break;
                    case "sensing":
                        sensingBlocksForStage++;
                        break;
                    case "sound":
                        soundBlocksForStage++;
                        break;
                    default:
                        break;
                }
            }
        }

        // Check for additional array elements, which represent embedded blocks.
        for (int i = 0; i < scripts.size(); i++)
        {
            if (scripts.get(i) instanceof JSONArray)
            {
                processScripts((JSONArray) scripts.get(i));
            }
        }

        return;
    }

    /**
     * Get control block count.
     *
     * @return count
     */
    public int getControlBlocksForStage()
    {
        return controlBlocksForStage;
    }

    /**
     * Get data block count.
     *
     * @return count
     */
    public int getDataBlocksForStage()
    {
        return dataBlocksForStage;
    }
    
    /**
     * Get events block count.
     *
     * @return count
     */
    public int getEventsBlocksForStage()
    {
        return eventsBlocksForStage;
    }
    
    /**
     * Get looks block count.
     *
     * @return count
     */
    public int getLooksBlocksForStage()
    {
        return looksBlocksForStage;
    }
    
    /**
     * Get more blocks block count.
     *
     * @return count
     */
    public int getMoreBlocksBlocksForStage()
    {
        return moreBlocksBlocksForStage;
    }
    
    /**
     * Get motion block count.
     * Should always be 0 - motion blocks are not available for stage.
     *
     * @return count
     */
    public int getMotionBlocksForStage()
    {
        return motionBlocksForStage;
    }
    
    /**
     * Get operators block count.
     *
     * @return count
     */
    public int getOperatorsBlocksForStage()
    {
        return operatorsBlocksForStage;
    }
    
    /**
     * Get pen block count.
     *
     * @return count
     */
    public int getPenBlocksForStage()
    {
        return penBlocksForStage;
    }
    
    /**
     * Get sensing block count.
     *
     * @return count
     */
    public int getSensingBlocksForStage()
    {
        return sensingBlocksForStage;
    }
    
    /**
     * Get sound block count.
     *
     * @return count
     */
    public int getSoundBlocksForStage()
    {
        return soundBlocksForStage;
    }

    /**
     * Get variable count for program.
     *
     * @return count
     */
    public int getVariableCountForProgram()
    {
        int count = 0;
        if (getSpriteCount() > 0)
        {
            for (int i = 0; i < sprites.length; i++)
            {
                count += sprites[i].getVariableCount();
            }
        }
        return count + getVariableCountForStage();
    }
    
    /**
     * Get list count for program.
     *
     * @return count
     */
    public int getListCountForProgram()
    {
        int count = 0;
        if (getSpriteCount() > 0)
        {
            for (int i = 0; i < sprites.length; i++)
            {
                count += sprites[i].getListCount();
            }
        }
        return count + getListCountForStage();
    }
    
    /**
     * Get script comment count for program.
     *
     * @return count
     */
    public int getScriptCommentCountForProgram()
    {
        int count = 0;
        if (getSpriteCount() > 0)
        {
            for (int i = 0; i < sprites.length; i++)
            {
                count += sprites[i].getScriptCommentCount();
            }
        }
        return count + getScriptCommentCountForStage();
    }
    
    /**
     * Get sound count for program.
     *
     * @return count
     */
    public int getSoundCountForProgram()
    {
        int count = 0;
        if (getSpriteCount() > 0)
        {
            for (int i = 0; i < sprites.length; i++)
            {
                count += sprites[i].getSoundCount();
            }
        }
        return count + getSoundCountForStage();
    }
    
    /**
     * Get costume count for program.
     *
     * @return count
     */
    public int getCostumeCountForProgram()
    {
        int count = 0;
        if (getSpriteCount() > 0)
        {
            for (int i = 0; i < sprites.length; i++)
            {
                count += sprites[i].getCostumeCount();
            }
        }
        return count + getCostumeCountForStage();
    }

    /**
     * Get control block count for program.
     *
     * @return count
     */
    public int getControlBlocksForProgram()
    {
        int count = 0;
        if (getSpriteCount() > 0)
        {
            for (int i = 0; i < sprites.length; i++)
            {
                count += sprites[i].getControlBlocksForSprite();
            }
        }
        return count + controlBlocksForStage;
    }
    
    /**
     * Get data block count for program.
     *
     * @return count
     */
    public int getDataBlocksForProgram()
    {
        int count = 0;
        if (getSpriteCount() > 0)
        {
            for (int i = 0; i < sprites.length; i++)
            {
                count += sprites[i].getDataBlocksForSprite();
            }
        }
        return count + dataBlocksForStage;
    }
    
    /**
     * Get events block count for program.
     *
     * @return count
     */
    public int getEventsBlocksForProgram()
    {
        int count = 0;
        if (getSpriteCount() > 0)
        {
            for (int i = 0; i < sprites.length; i++)
            {
                count += sprites[i].getEventsBlocksForSprite();
            }
        }
        return count + eventsBlocksForStage;
    }
    
    /**
     * Get looks block count for program.
     *
     * @return count
     */
    public int getLooksBlocksForProgram()
    {
        int count = 0;
        if (getSpriteCount() > 0)
        {
            for (int i = 0; i < sprites.length; i++)
            {
                count += sprites[i].getLooksBlocksForSprite();
            }
        }
        return count + looksBlocksForStage;
    }
    
    /**
     * Get more blocks block count for program.
     *
     * @return count
     */
    public int getMoreBlocksBlocksForProgram()
    {
        int count = 0;
        if (getSpriteCount() > 0)
        {
            for (int i = 0; i < sprites.length; i++)
            {
                count += sprites[i].getMoreBlocksBlocksForSprite();
            }
        }
        return count + moreBlocksBlocksForStage;
    }
    
    /**
     * Get motion block count for program.
     *
     * @return count
     */
    public int getMotionBlocksForProgram()
    {
        int count = 0;
        if (getSpriteCount() > 0)
        {
            for (int i = 0; i < sprites.length; i++)
            {
                count += sprites[i].getMotionBlocksForSprite();
            }
        }
        return count + motionBlocksForStage;
    }
    
    /**
     * Get operators block count for program.
     *
     * @return count
     */
    public int getOperatorsBlocksForProgram()
    {
        int count = 0;
        if (getSpriteCount() > 0)
        {
            for (int i = 0; i < sprites.length; i++)
            {
                count += sprites[i].getOperatorsBlocksForSprite();
            }
        }
        return count + operatorsBlocksForStage;
    }
    
    /**
     * Get pen block count for program.
     *
     * @return count
     */
    public int getPenBlocksForProgram()
    {
        int count = 0;
        if (getSpriteCount() > 0)
        {
            for (int i = 0; i < sprites.length; i++)
            {
                count += sprites[i].getPenBlocksForSprite();
            }
        }
        return count + penBlocksForStage;
    }
    
    /**
     * Get sensing block count for program.
     *
     * @return count
     */
    public int getSensingBlocksForProgram()
    {
        int count = 0;
        if (getSpriteCount() > 0)
        {
            for (int i = 0; i < sprites.length; i++)
            {
                count += sprites[i].getSensingBlocksForSprite();
            }
        }
        return count + sensingBlocksForStage;
    }
    
    /**
     * Get sound block count for program.
     *
     * @return count
     */
    public int getSoundBlocksForProgram()
    {
        int count = 0;
        if (getSpriteCount() > 0)
        {
            for (int i = 0; i < sprites.length; i++)
            {
                count += sprites[i].getSoundBlocksForSprite();
            }
        }
        return count + soundBlocksForStage;
    }

    /**
     * Get category name for specified script name.
     *
     * @param scriptName 
     * @return category, null if no mapping
     */
    protected static String getCategory(String scriptName)
    {
        return (String) categoryMap.get(scriptName);
    }
    
    /**
     * HashMap to store script names with block category.
     * Control scripts.
     */
    private void addControlCategoryMap()
    {
        categoryMap.put("wait:elapsed:from:", "control");
        categoryMap.put("wait:elapsed:from:", "control");
        categoryMap.put("doRepeat", "control");
        categoryMap.put("doForever", "control");
        categoryMap.put("doIf", "control");
        categoryMap.put("doIfElse", "control");
        categoryMap.put("doWaitUntil", "control");
        categoryMap.put("doUntil", "control");
        categoryMap.put("stopScripts", "control");
        categoryMap.put("whenCloned", "control");
        categoryMap.put("createCloneOf", "control");
        categoryMap.put("deleteClone", "control");
    }
    
    /**
     * HashMap to store script names with block category.
     * Data scripts.
     */
    private void addDataCategoryMap()
    {
        categoryMap.put("readVariable", "data");
        categoryMap.put("setVar:to:", "data");
        categoryMap.put("changeVar:by:", "data");
        categoryMap.put("showVariable:", "data");
        categoryMap.put("hideVariable:", "data");
        categoryMap.put("contentsOfList:", "data");
        categoryMap.put("append:toList:", "data");
        categoryMap.put("deleteLine:ofList:", "data");
        categoryMap.put("insert:at:ofList:", "data");
        categoryMap.put("setLine:ofList:to:", "data");
        categoryMap.put("getLine:ofList:", "data");
        categoryMap.put("lineCountOfList:", "data");
        categoryMap.put("list:contains:", "data");
        categoryMap.put("showList:", "data");
        categoryMap.put("hideList:", "data");
    }
    
    /**
     * HashMap to store script names with block category.
     * Events scripts.
     */
    private void addEventsCategoryMap()
    {
        categoryMap.put("whenGreenFlag", "events");
        categoryMap.put("whenKeyPressed", "events");
        categoryMap.put("whenClicked", "events");
        categoryMap.put("whenSceneStarts", "events");
        categoryMap.put("whenSensorGreaterThan", "events");
        categoryMap.put("whenIReceive", "events");
        categoryMap.put("broadcast:", "events");
        categoryMap.put("doBroadcastAndWait", "events");
    }
    
    /**
     * HashMap to store script names with block category.
     * Looks scripts.
     */
    private void addLooksCategoryMap()
    {
        categoryMap.put("say:duration:elapsed:from:", "looks");
        categoryMap.put("say:", "looks");
        categoryMap.put("think:duration:elapsed:from:", "looks");
        categoryMap.put("think:", "looks");
        categoryMap.put("show", "looks");
        categoryMap.put("hide", "looks");
        categoryMap.put("lookLike:", "looks");
        categoryMap.put("nextCostume", "looks");
        categoryMap.put("startScene", "looks");
        categoryMap.put("changeGraphicEffect:by:", "looks");
        categoryMap.put("setGraphicEffect:to:", "looks");
        categoryMap.put("filterReset", "looks");
        categoryMap.put("changeSizeBy:", "looks");
        categoryMap.put("setSizeTo:", "looks");
        categoryMap.put("comeToFront", "looks");
        categoryMap.put("goBackByLayers:", "looks");
        categoryMap.put("costumeIndex", "looks");
        categoryMap.put("sceneName", "looks");
        categoryMap.put("scale", "looks");
    }
    
    /**
     * HashMap to store script names with block category.
     * More blocks scripts.
     */
    private void addMoreBlocksCategoryMap()
    {
        categoryMap.put("procDef", "more blocks");
        categoryMap.put("call", "more blocks");
        categoryMap.put("LEGO WeDo\u001fmotorOnFor", "more blocks");
        categoryMap.put("LEGO WeDo\u001fmotorOn", "more blocks");
        categoryMap.put("LEGO WeDo\u001fmotorOff", "more blocks");
        categoryMap.put("LEGO WeDo\u001fstartMotorPower", "more blocks");
        categoryMap.put("LEGO WeDo\u001fsetMotorDirection", "more blocks");
        categoryMap.put("LEGO WeDo\u001fwhenDistance", "more blocks");
        categoryMap.put("LEGO WeDo\u001fwhenTilt", "more blocks");
        categoryMap.put("LEGO WeDo\u001fgetDistance", "more blocks");
        categoryMap.put("LEGO WeDo\u001fgetTilt", "more blocks");
        categoryMap.put("LEGO WeDo 2.0\u001fmotorOnFor", "more blocks");
        categoryMap.put("LEGO WeDo 2.0\u001fmotorOn", "more blocks");
        categoryMap.put("LEGO WeDo 2.0\u001fmotorOff", "more blocks");
        categoryMap.put("LEGO WeDo 2.0\u001fstartMotorPower", "more blocks");
        categoryMap.put("LEGO WeDo 2.0\u001fsetMotorDirection", "more blocks");
        categoryMap.put("LEGO WeDo 2.0\u001fsetLED", "more blocks");
        categoryMap.put("LEGO WeDo 2.0\u001fplayNote", "more blocks");
        categoryMap.put("LEGO WeDo 2.0\u001fwhenDistance", "more blocks");
        categoryMap.put("LEGO WeDo 2.0\u001fwhenTilted", "more blocks");
        categoryMap.put("LEGO WeDo 2.0\u001fgetDistance", "more blocks");
        categoryMap.put("LEGO WeDo 2.0\u001fisTilted", "more blocks");
        categoryMap.put("LEGO WeDo 2.0\u001fgetTilt", "more blocks");
        categoryMap.put("PicoBoard\u001fwhenSensorConnected", "more blocks");
        categoryMap.put("PicoBoard\u001fwhenSensorPass", "more blocks");
        categoryMap.put("PicoBoard\u001fsensorPressed", "more blocks");
        categoryMap.put("PicoBoard\u001fsensor", "more blocks");
    }
    
    /**
     * HashMap to store script names with block category.
     * Motion scripts.
     */
    private void addMotionCategoryMap()
    {
        categoryMap.put("forward:", "motion");
        categoryMap.put("turnRight:", "motion");
        categoryMap.put("turnLeft:", "motion");
        categoryMap.put("heading:", "motion");
        categoryMap.put("pointTowards:", "motion");
        categoryMap.put("gotoX:y:", "motion");
        categoryMap.put("gotoSpriteOrMouse:", "motion");
        categoryMap.put("glideSecs:toX:y:elapsed:from:", "motion");
        categoryMap.put("changeXposBy:", "motion");
        categoryMap.put("xpos:", "motion");
        categoryMap.put("changeYposBy:", "motion");
        categoryMap.put("ypos:", "motion");
        categoryMap.put("bounceOffEdge", "motion");
        categoryMap.put("setRotationStyle", "motion");
    }
    
    /**
     * HashMap to store script names with block category.
     * Operators scripts.
     */
    private void addOperatorsCategoryMap()
    {
        categoryMap.put("+", "operators");
        categoryMap.put("-", "operators");
        categoryMap.put("*", "operators");
        categoryMap.put("\\/", "operators");
        categoryMap.put("randomFrom:to:", "operators");
        categoryMap.put("<", "operators");
        categoryMap.put("=", "operators");
        categoryMap.put(">", "operators");
        categoryMap.put("&", "operators");
        categoryMap.put("|", "operators");
        categoryMap.put("not", "operators");
        categoryMap.put("concatenate:with:", "operators");
        categoryMap.put("letter:of:", "operators");
        categoryMap.put("stringLength:", "operators");
        categoryMap.put("%", "operators");
        categoryMap.put("rounded", "operators");
        categoryMap.put("computeFunction:of:", "operators");
    }
    
    /**
     * HashMap to store script names with block category.
     * Pen scripts.
     */
    private void addPenCategoryMap()
    {
        categoryMap.put("clearPenTrails", "pen");
        categoryMap.put("stampCostume", "pen");
        categoryMap.put("putPenDown", "pen");
        categoryMap.put("putPenUp", "pen");
        categoryMap.put("penColor:", "pen");
        categoryMap.put("changePenHueBy:", "pen");
        categoryMap.put("setPenHueTo:", "pen");
        categoryMap.put("changePenShadeBy:", "pen");
        categoryMap.put("setPenShadeTo:", "pen");
        categoryMap.put("changePenSizeBy:", "pen");
        categoryMap.put("penSize:", "pen");
    }
    
    /**
     * HashMap to store script names with block category.
     * Sensing scripts.
     */
    private void addSensingCategoryMap()
    {
        categoryMap.put("touching:", "sensing");
        categoryMap.put("touchingColor:", "sensing");
        categoryMap.put("color:sees:", "sensing");
        categoryMap.put("distanceTo:", "sensing");
        categoryMap.put("doAsk", "sensing");
        categoryMap.put("answer", "sensing");
        categoryMap.put("keyPressed:", "sensing");
        categoryMap.put("mousePressed", "sensing");
        categoryMap.put("mouseX", "sensing");
        categoryMap.put("mouseY", "sensing");
        categoryMap.put("soundLevel", "sensing");
        categoryMap.put("senseVideoMotion", "sensing");
        categoryMap.put("setVideoState", "sensing");
        categoryMap.put("setVideoTransparency", "sensing");
        categoryMap.put("timer", "sensing");
        categoryMap.put("timerReset", "sensing");
        categoryMap.put("getAttribute:of:", "sensing");
        categoryMap.put("timeAndDate", "sensing");
        categoryMap.put("timestamp", "sensing");
        categoryMap.put("getUserName", "sensing");
    }
    
    /**
     * HashMap to store script names with block category.
     * Sound scripts.
     */
    private void addSoundCategoryMap()
    {
        categoryMap.put("playSound:", "sound");
        categoryMap.put("doPlaySoundAndWait", "sound");
        categoryMap.put("stopAllSounds", "sound");
        categoryMap.put("playDrum", "sound");
        categoryMap.put("rest:elapsed:from:", "sound");
        categoryMap.put("noteOn:duration:elapsed:from:", "sound");
        categoryMap.put("instrument:", "sound");
        categoryMap.put("changeVolumeBy:", "sound");
        categoryMap.put("setVolumeTo:", "sound");
        categoryMap.put("volume", "sound");
        categoryMap.put("changeTempoBy:", "sound");
        categoryMap.put("setTempoTo:", "sound");
        categoryMap.put("tempo", "sound");
    }

    /**
     * Get the variables array.
     *
     * @return array of variables
     */
    public String[] getGlobalVariables()
    {
        return globalVariables;
    }

    /**
     * Get the list array.
     *
     * @return global list array
     */
    public String[] getGlobalLists()
    {
        return globalLists;
    }

    /**
     * Populate the global variables array.
     */
    private void populateGlobalVariables()
    {
        JSONArray vars =
            FileUtils.getJSONArrayAttribute(jsonObj, "variables");
        globalVariables = new String[vars.size()];
        JSONObject children = new JSONObject();
        int j = 0;
        for (int i = 0; i < vars.size(); i++)
        {
            children = (JSONObject) vars.get(i);
            globalVariables[j] = (String) children.get("name");  
            j++; 
        }
    }

    /**
     * Populate the global lists array.
     */
    private void populateGlobalLists()
    {
        JSONArray lists =
            FileUtils.getJSONArrayAttribute(jsonObj, "lists");
        globalLists = new String[lists.size()];
        JSONObject children = new JSONObject();
        int j = 0;
        for (int i = 0; i < lists.size(); i++)
        {
            children = (JSONObject) lists.get(i);
            globalLists[j] = (String) children.get("listName");
            j++;
        }
    }

    /**
     * Get total program variable usage counts.
     *
     * @param var - the variable to be counted
     * @return number of times variable used total
     */
    public int getProgramVariableUsageCount(String var)
    {
        int count = getStageVariableUsageCount(var);
        if (sprites == null)
        {
            return count;
        }
        for (int i = 0; i < sprites.length; i++)
        {
            count += sprites[i].getVariableUsageCount(var);
        }
        return count;
    }

    /**
     * Get total program list usage count.
     *
     * @param list - the list to be counted
     * @return number of times list used in total
     */
    public int getProgramListUsageCount(String list)
    {
        int count = getStageListUsageCount(list);
        if (sprites == null)
        {
            return count;
        }
        for (int i = 0; i < sprites.length; i++)
        {
            count += sprites[i].getListUsageCount(list);
        }
        return count;
    }

    /**
     * Get global variable usage count
     *  from stage scripts.
     *
     *  @param var the variable being counted
     *  @return number of times the variable is used in Stage
     */
    public int getStageVariableUsageCount(String var)
    {
        int count = 0;
        JSONArray stageJSON =
            FileUtils.getJSONArrayAttribute(jsonObj, "scripts"); 
        String stage = stageJSON.toString();
        int pos = stage.indexOf(var);
        while (pos >= 0)
        {
            pos += 1;
            count += 1;
            pos = stage.indexOf(var, pos);
        }
        return count;
    }

    /**
     * Get global list usage count.
     *
     * @param list the list being counted
     * @return number of times the list is used in Stage
     */
    public int getStageListUsageCount(String list)
    {
        int count = 0;
        JSONArray stageJSON =
            FileUtils.getJSONArrayAttribute(jsonObj, "scripts");
        String stage = stageJSON.toString();
        int pos = stage.indexOf(list);
        while (pos >= 0)
        {
            pos += 1;
            count += 1;
            pos = stage.indexOf(list, pos);
        }
        return count;
    }
}


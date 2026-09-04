import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PatientSystemGUI {
    private static byte[] attachmentBytes = null;
    private static String attachmentName = null;
    private static String maskKey(String k) {
        if (k == null) return "(null)";
        if (k.length() <= 8) return k.substring(0, Math.min(4, k.length())) + "...";
        return k.substring(0, 6) + "..." + k.substring(k.length()-2);
    }
 public static void main(String[] args) {

    try {
        String dbPassword = System.getenv("DB_PASSWORD");

Connection conn = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/patientdb",
    "root",
    dbPassword
);
        System.out.println("Connected Successfully!");

        // Debug: show whether GOOGLE_API_KEY is present (masked)
        String dbgKey = System.getenv("GOOGLE_API_KEY");
        System.out.println("DEBUG: GOOGLE_API_KEY present: " + (dbgKey == null ? "NO" : "YES, " + maskKey(dbgKey)) );

        conn.close();

    } catch (Exception e) {
       e.printStackTrace();
    }



        //window create
        JFrame frame = new JFrame("Digital Patient System");
        frame.setSize(1250, 940);
       frame.setIconImage(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\logo.jpeg").getImage());
        frame.setLayout(null);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setResizable(true);   
        frame.getContentPane().setBackground( new Color(232, 248, 242));
        JPanel dashboard = new JPanel();

int dashboardWidth = 1150;
int dashboardX = (1250 - dashboardWidth) / 2;
dashboard.setBounds(dashboardX, 30, dashboardWidth, 860);

dashboard.setLayout(null);

dashboard.setBackground(Color.WHITE);

dashboard.setBorder(
    BorderFactory.createLineBorder(
        new Color(200,230,218),
        1
    )
);

frame.add(dashboard);

// Dashboard ko hamesha window ke center mein rakhne ke liye
Runnable centerDashboard = () -> {
    int frameW = frame.getContentPane().getWidth();
    int frameH = frame.getContentPane().getHeight();
    int dashW = dashboard.getWidth();
    int dashH = dashboard.getHeight();
    int x = Math.max(0, (frameW - dashW) / 2);
    int y = Math.max(0, (frameH - dashH) / 2);
    dashboard.setLocation(x, y);
};

frame.addComponentListener(new java.awt.event.ComponentAdapter() {
    public void componentResized(java.awt.event.ComponentEvent e) {
        centerDashboard.run();
    }
});

        // Title with icon
        JLabel title = new JLabel("  Patient Health Checker");

title.setBounds(320, 20, 500, 50);

title.setFont(
    new Font("Segoe UI", Font.BOLD, 34)
);

title.setForeground(new Color(15, 110, 86));

JLabel subtitle = new JLabel(
    "  Enter Patient Details & Get AI Medical Advice"
);

subtitle.setBounds(360, 65, 420, 25);

subtitle.setFont(
    new Font("Segoe UI", Font.PLAIN, 15)
);

subtitle.setForeground(
    new Color(93,165,146)
);
// Logo top-left

ImageIcon logoIcon = new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\logo.jpeg");
Image logoImg = logoIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
logoLabel.setBounds(30, 15, 70, 70);
dashboard.add(logoLabel);
dashboard.add(subtitle);
dashboard.add(title);
JPanel formPanel = new JPanel();

formPanel.setBounds(40, 110, 450, 650);

formPanel.setLayout(null);

formPanel.setBackground(Color.WHITE);

formPanel.setBorder(
    BorderFactory.createLineBorder(
        new Color(214,236, 226),
        1
    )
);

dashboard.add(formPanel);

JPanel uploadPanel = new JPanel();

uploadPanel.setBounds(550, 110, 550, 320);

uploadPanel.setLayout(null);

uploadPanel.setBackground(Color.WHITE);

uploadPanel.setBorder(
    BorderFactory.createLineBorder(
        new Color(214,236,226),
        1
    )
);

dashboard.add(uploadPanel);

JLabel uploadTitle = new JLabel(
    "Upload Report (Optional)"
);

uploadTitle.setBounds(20,20,300,30);

uploadTitle.setFont(
    new Font("Segoe UI", Font.BOLD, 20)
);

uploadTitle.setForeground(
    new Color(70,70,70)
);

uploadPanel.add(uploadTitle);
JPanel dragBox = new JPanel();

dragBox.setBounds(75, 60, 400, 180);

dragBox.setBackground(
    new Color(248,250,255)
);

dragBox.setBorder(
    BorderFactory.createDashedBorder(
        new Color(120,140,220)
    )
);

dragBox.setLayout(null);

uploadPanel.add(dragBox);
JLabel dragText = new JLabel(
    "Drag & Drop Report Image Here"
);

dragText.setBounds(60, 15, 280, 30);

dragText.setFont(
    new Font("Segoe UI", Font.PLAIN, 16)
);

dragBox.add(dragText);
JButton chooseBtn =
        new JButton("Choose Image");

chooseBtn.setBounds(120, 100, 160, 42);

chooseBtn.setFocusPainted(false);

chooseBtn.setBackground(
    new Color(15,110,86)
);

chooseBtn.setForeground(Color.WHITE);

chooseBtn.setFont(
    new Font("Segoe UI", Font.BOLD, 15)
);

dragBox.add(chooseBtn);
JLabel imagePreview = new JLabel();

imagePreview.setBounds(195, 260, 160, 45);

imagePreview.setBorder(
    BorderFactory.createLineBorder(Color.GRAY)
);

uploadPanel.add(imagePreview);

        

        // Labels with icons
        JLabel nameLabel = new JLabel(" Name:");
        JLabel dateLabel = new JLabel(" Date:");
         JLabel genderLabel = new JLabel(" Gender:");
       JLabel ageLabel = new JLabel(" Age:");
        JLabel bpLabel = new JLabel(" BP:");
        JLabel sugarLabel = new JLabel(" Sugar:");
        JLabel feverLabel = new JLabel(" Fever (°C):");

        JLabel[] labels = {nameLabel,dateLabel,genderLabel, ageLabel, bpLabel, sugarLabel,feverLabel};

        int y = 30;
        for (JLabel label : labels) {


           label.setBounds(30, y, 120, 35);
            label.setForeground(
    new Color(40,40,40)
);
            label.setFont(new Font("Arial", Font.BOLD, 20));
           formPanel.add(label);
            y += 62;
        }

        // Text Fields
        JTextField nameField = new JTextField();
        java.time.LocalDate today =
        java.time.LocalDate.now();

        JTextField dateField =
        new JTextField(today.toString());
       String[] genders = {"Male", "Female", "Other"};

JComboBox<String> genderField =
        new JComboBox<>(genders);

genderField.setBounds(350, 180, 180, 30);

genderField.setFont(
        new Font("Arial", Font.PLAIN, 14)
);


        JTextField ageField = new JTextField();
        JTextField bpField = new JTextField();
        JTextField sugarField = new JTextField();
        JTextField feverField = new JTextField();

      nameField.setBounds(160, 30, 230, 35);

     dateField.setBounds(160, 92, 230, 35);

     genderField.setBounds(160, 154, 230, 35);

     ageField.setBounds(160, 216, 230, 35);

     bpField.setBounds(160, 278, 230, 35);

     sugarField.setBounds(160, 340, 230, 35);

     feverField.setBounds(160, 402, 230, 35);


JTextField[] fields = {
    nameField,
    dateField,
    ageField,
    bpField,
    sugarField,
    feverField
};

for (JTextField field : fields) {

    field.setFont(new Font("Arial", Font.PLAIN, 14));

    field.setBorder(
        BorderFactory.createLineBorder(Color.GRAY, 1)
    );

  formPanel.add(field);
}

genderField.setFont(
    new Font("Arial", Font.PLAIN, 14)
);

formPanel.add(genderField);

        // Button with icon
        JButton checkButton = new JButton("  Check");
        checkButton.setBounds(110, 460, 230, 45);
        checkButton.setForeground(Color.WHITE);
        checkButton.setBackground(new Color(15, 110, 86));
        checkButton.setOpaque(true);

checkButton.setFont(
    new Font("Segoe UI", Font.BOLD, 18)
);

checkButton.setBorderPainted(false);

        // Result Label
     





        // Button Action
        chooseBtn.addActionListener(e -> {

        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
        java.io.File file = chooser.getSelectedFile();
        attachmentName = file.getName();
        try {
            java.nio.file.Path p = file.toPath();
            attachmentBytes = java.nio.file.Files.readAllBytes(p);
        } catch (Exception ex) {
            attachmentBytes = null;
            attachmentName = null;
        }

        // Show simple preview for images; show filename for other types (e.g., PDF)
        String lower = file.getName().toLowerCase();
        if (lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".gif") ) {
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            Image img = icon.getImage().getScaledInstance(200, 70, Image.SCALE_SMOOTH);
            imagePreview.setIcon(new ImageIcon(img));
            imagePreview.setText("");
        } else {
            imagePreview.setIcon(null);
            imagePreview.setText("Selected: " + file.getName());
        }
        }
    });

JPanel aiPanel = new JPanel();

aiPanel.setBounds(550, 450, 550, 380);

aiPanel.setLayout(null);

aiPanel.setBackground(
    new Color(240,250,246)
);

aiPanel.setBorder(
    BorderFactory.createLineBorder(
        new Color(200,230,218)
    )
);

dashboard.add(aiPanel);

JLabel aiTitle = new JLabel("AI Medical Analysis");

aiTitle.setBounds(20, 10, 250, 25);

aiTitle.setFont(
    new Font("Segoe UI", Font.BOLD, 20)
);

aiTitle.setForeground(
    new Color(15,90,75)
);

aiPanel.add(aiTitle);

JLabel resultLabel = new JLabel();

resultLabel.setVerticalAlignment(
    SwingConstants.TOP
);

resultLabel.setFont(
    new Font("Segoe UI", Font.PLAIN, 16)
);

JScrollPane resultScroll = new JScrollPane(resultLabel);
resultScroll.setBounds(20, 50, 510, 310);
resultScroll.setBorder(null);
resultScroll.getVerticalScrollBar().setUnitIncrement(16);
resultScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

aiPanel.add(resultScroll);
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                 String name = nameField.getText().trim();

if(name.isEmpty()) {

   resultLabel.setForeground(
    new Color(180,40,40)
);

    resultLabel.setText("Enter Patient Name!");

    return;
}



             int bp = -1;

if(!bpField.getText().trim().isEmpty()) {

    bp = Integer.parseInt(
            bpField.getText()
    );
}
int sugar = -1;

if(!sugarField.getText().trim().isEmpty()) {

    sugar = Integer.parseInt(
            sugarField.getText()
    );
}

float fever = -1;

if(!feverField.getText().trim().isEmpty()) {

    fever = Float.parseFloat(
            feverField.getText()
    );
}
if(bp == -1 &&
   sugar == -1 &&
   fever == -1) {

    resultLabel.setForeground(Color.RED);

    resultLabel.setText(
        "Enter at least one health parameter!"
    );

    return;
}

String result = "";


// BP
if(bp != -1) {

    if (bp < 90) {

        result += "Low BP";

    }
    else if (bp <= 120) {

        result += "Normal BP";

    }
    else if (bp <= 140) {

        result += "Pre-High BP";

    }
    else {

        result += "High BP";
    }
}



// Sugar
if(sugar != -1) {

    if(!result.isEmpty()) {

        result += " | ";
    }

    if (sugar < 70) {

        result += "Low Sugar";

    }
    else if (sugar <= 140) {

        result += "Normal Sugar";

    }
    else if (sugar <= 180) {

        result += "Pre-Diabetic";

    }
    else {

        result += "High Sugar";
    }
}



// Fever
if(fever != -1) {

    if(!result.isEmpty()) {

        result += " | ";
    }

    if (fever < 36) {

        result += "Low Temp";

    }
    else if (fever <= 37.5) {

        result += "Normal Temp";

    }
    else if (fever <= 38.5) {

        result += "Mild Fever";

    }
    else {

        result += "High Fever";
    }
}



// 🔹 Final Summary Color
if (bp > 140 || sugar > 180 || fever > 38.5) {
    resultLabel.setForeground(Color.RED);
} else if (bp > 120 || sugar > 140 || fever > 37.5) {
    resultLabel.setForeground(Color.BLUE);
} else {
    resultLabel.setForeground(Color.ORANGE);
}

// 🔹 Show Result
resultLabel.setText(result);
                     

                   java.sql.Date sqlDate;

try {
    sqlDate = java.sql.Date.valueOf(dateField.getText().trim());
} catch (Exception e1) {
    resultLabel.setText("Invalid Date Format! Use yyyy-mm-dd");
    return;
}

               String dbPassword = System.getenv("DB_PASSWORD");

Connection conn = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/patientdb",
    "root",
    dbPassword
);

            String query = "INSERT INTO patients (name, date, gender, age, bp, sugar,fever) VALUES (?, ?, ?, ?, ?, ?,?)";

            PreparedStatement pst = conn.prepareStatement(query);

            pst.setString(1, nameField.getText());
            pst.setDate(2, java.sql.Date.valueOf(dateField.getText()));
            pst.setString(
    3,
    genderField.getSelectedItem().toString()
);
 
      if(ageField.getText().trim().isEmpty()) {

    pst.setNull(4, java.sql.Types.INTEGER);

}
else {

    pst.setInt(
        4,
        Integer.parseInt(ageField.getText())
    );
}
      
         if(bp == -1) {

    pst.setNull(5, java.sql.Types.INTEGER);

}
else {

    pst.setInt(5, bp);
}



if(sugar == -1) {

    pst.setNull(6, java.sql.Types.INTEGER);

}
else {

    pst.setInt(6, sugar);
}



if(fever == -1) {

    pst.setNull(7, java.sql.Types.FLOAT);

}
else {

    pst.setFloat(7, fever);
}


            pst.executeUpdate();

            System.out.println("Data Inserted!");

               conn.close();

   resultLabel.setText(result);

   final String finalResult = result;

   final int finalBp = bp;

final int finalSugar = sugar;

final float finalFever = fever;
   

new Thread(() -> {

    try {

        System.out.println("AI Thread Started");

        // PROMPT
String prompt =

"You are a professional medical assistant AI. " +
"You MUST always return ALL THREE sections below, never skip Precautions even if data is normal. " +
"Respond in this EXACT format, nothing else:\n\n" +

"Condition:\n" +
"- (one line about current health status based on BP/Sugar/Fever and report if given)\n\n" +

"Main Risk:\n" +
"- (one line about biggest health risk right now)\n\n" +

"Precautions:\n" +
"1. (specific actionable precaution)\n" +
"2. (specific actionable precaution)\n" +
"3. (specific actionable precaution)\n\n" +

"Rules:\n" +
"- Precautions must be specific to the condition (e.g. for High BP: reduce salt, avoid stress; for High Sugar: avoid sugar, exercise).\n" +
"- Keep total response under 120 words.\n" +
"- Do not add any extra headings or disclaimers.\n\n" +

"Patient Data:\n" +

"BP: " + finalBp + "\n" +

"Sugar: " + finalSugar + "\n" +

"Fever: " + finalFever;

       // If an attachment is provided, instruct the model to decode and analyze it
              // If an attachment is provided, instruct the model to analyze it (actual file bytes are sent separately below)
              // If an attachment is provided, instruct the model to analyze it (actual file bytes are sent separately below)
       if (attachmentBytes != null && attachmentName != null) {
           prompt += "\n\nA medical report file named \"" + attachmentName + "\" is also attached as an image/PDF. "
                   + "Read the specific test values, ranges, and any flagged/abnormal results printed in this report. "
                   + "Explicitly mention 2-3 key findings from the report (with their actual values) in the Condition section, "
                   + "and factor them into the Main Risk and Precautions sections as well.";
       }

      


    String safePrompt = prompt
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", " ");

    SwingUtilities.invokeLater(() -> {

    resultLabel.setForeground(
        new Color(40, 90, 180)
    );

    resultLabel.setText(
        "<html>"
        + "<b>Analyzing Patient Report...</b><br>"
        + "Please wait while AI generates advice..."
        + "</html>"
    );

});

    String aiText = "No AI Response";

    try {
        //String endpoint = System.getenv("GOOGLE_FLASH_ENDPOINT");
        String apiKey = System.getenv("GOOGLE_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            SwingUtilities.invokeLater(() -> {
                resultLabel.setForeground(new Color(180,40,40));
                resultLabel.setText(
                    "<html><b>AI Disabled</b><br>Configure GOOGLE_API_KEY to enable AI.</html>"
                );
            });
            return;
        }
        //aiText = callGoogleFlashAPI(safePrompt, endpoint, apiKey);
        aiText = callGoogleFlashAPI(prompt, attachmentName, attachmentBytes, apiKey);

    } catch(Exception ex) {
    aiText = "AI Parsing Failed";
}

// Format AI text for HTML display
try {
    aiText = formatAiResponse(aiText);
} catch (Exception _fmt) {
    // ignore formatting errors
}

final String finalAiText = aiText;
SwingUtilities.invokeLater(() -> {

    // andar wale <html> tags hatao taaki nesting na ho
    String cleanAiHtml = finalAiText
            .replace("<html>", "")
            .replace("</html>", "")
            .replace("<body style='width:480px;font-family:Segoe UI'>", "")
            .replace("</body>", "");

    resultLabel.setText(
            "<html><body style='width:480px'>"
            + finalResult
            + "<br><br>"
            + cleanAiHtml
            + "</body></html>"
    );

});

    } catch (Exception ex2) {

        ex2.printStackTrace();

        SwingUtilities.invokeLater(() -> {

           resultLabel.setText(
    "<html>"
    + "<b>AI Server Busy</b><br>"
    + "Please wait few seconds and try again."
    + "</html>"
);

        });
    }

}).start();

       

        

                } catch (Exception ex) {
                    resultLabel.setForeground(Color.BLACK);
                    resultLabel.setText("Enter valid numbers!");
                    
                    ex.printStackTrace();

                     System.out.println(ex);
                }
            }
        });

        // Add components
      dashboard.add(title);

formPanel.add(checkButton);

        frame.setLocationRelativeTo(null);
frame.setVisible(true);
centerDashboard.run();
    }

  // <-- ye closing brace "public static void main(String[] args) {" ko band karta hai, ISE MAT HATANA

    private static String callGoogleFlashAPI(String promptText, String attachmentName, byte[] attachmentBytes, String apiKey) throws Exception {

        String model = "gemini-2.5-flash";
        String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent?key=" + apiKey;

        StringBuilder partsJson = new StringBuilder();
        partsJson.append("{\"text\":\"").append(escapeJson(promptText)).append("\"}");

        if (attachmentBytes != null && attachmentName != null) {
            String mimeType = guessMimeType(attachmentName);
            String b64 = java.util.Base64.getEncoder().encodeToString(attachmentBytes);
            partsJson.append(",{\"inline_data\":{\"mime_type\":\"").append(mimeType)
                     .append("\",\"data\":\"").append(b64).append("\"}}");
        }

        String payload = "{"
        + "\"contents\":[{\"parts\":[" + partsJson + "]}],"
        + "\"generationConfig\":{"
        +     "\"maxOutputTokens\":1024,"
        +     "\"temperature\":0.4,"
        +     "\"thinkingConfig\":{\"thinkingBudget\":0}"
        + "}"
        + "}";

        java.net.URL url = new java.net.URL(endpoint);
        java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(40000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        try (java.io.OutputStream os = conn.getOutputStream()) {
            os.write(payload.getBytes("UTF-8"));
            os.flush();
        }

        int status = conn.getResponseCode();
        java.io.InputStream is = (status == 200) ? conn.getInputStream() : conn.getErrorStream();
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();

        String resp = sb.toString();

        if (status != 200) {
            return "AI Error (HTTP " + status + "): " + resp;
        }

        String marker = "\"text\": \"";
        int idx = resp.indexOf(marker);
        if (idx == -1) {
            marker = "\"text\":\"";
            idx = resp.indexOf(marker);
        }
        if (idx != -1) {
            int start = idx + marker.length();
            StringBuilder out = new StringBuilder();
            for (int i = start; i < resp.length(); i++) {
                char c = resp.charAt(i);
                if (c == '"' && resp.charAt(i - 1) != '\\') break;
                out.append(c);
            }
            return out.toString().replace("\\n", "<br>");
        }

        return "AI response format unexpected: " + resp;
    }

private static String formatAiResponse(String aiText) {
    aiText = aiText.replace("\\n", "<br>");

    String condition = "";
    String risk = "";
    String precautions = "";

    String[] parts = aiText.split("Main Risk:");
    if (parts.length > 0) condition = parts[0].replace("Condition:", "").trim();
    if (parts.length > 1) {
        String[] parts2 = parts[1].split("Precautions:");
        risk = parts2[0].trim();
        if (parts2.length > 1) precautions = parts2[1].trim();
    }

    return "<table width='100%' cellpadding='10' cellspacing='0'><tr><td bgcolor='#E8F8F0'>"
        + "<font color='#0F6E56'><b>Condition</b></font><br>" + condition
        + "</td></tr></table><br>"

        + "<table width='100%' cellpadding='10' cellspacing='0'><tr><td bgcolor='#FFF3DE'>"
        + "<font color='#8A4B06'><b>Main Risk</b></font><br>" + risk
        + "</td></tr></table><br>"

        + "<table width='100%' cellpadding='10' cellspacing='0'><tr><td bgcolor='#EAF9E5'>"
        + "<font color='#1E8449'><b>Precautions</b></font><br>" + precautions
        + "</td></tr></table>";
}

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
    }

    private static String guessMimeType(String fileName) {
        String lower = fileName.toLowerCase();
        if (lower.endsWith(".png")) return "image/png";
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return "image/jpeg";
        if (lower.endsWith(".pdf")) return "application/pdf";
        if (lower.endsWith(".webp")) return "image/webp";
        return "application/octet-stream";
    }
}

 // <-- class PatientSystemGUI ko band karta hai
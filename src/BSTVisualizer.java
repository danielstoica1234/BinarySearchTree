import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

/**
 * K-Pop Demon Hunter: BST Containment Visualizer
 */
public class BSTVisualizer extends JFrame {

    private BinarySearchTree bst;
    private TreePanel treePanel;
    private JScrollPane treeScrollPane;
    private JTextArea outputArea;
    private JTextField inputField;
    private JLabel statusLabel;
    private JPanel controlPanel;

    // K-Pop Demon Hunter Palette
    private final Color NEON_PINK = new Color(255, 0, 127);
    private final Color CYBER_CYAN = new Color(0, 255, 255);
    private final Color DEEP_VOID = new Color(13, 13, 25);
    private final Color PANEL_DARK = new Color(25, 25, 45);
    private final Color SPIRIT_GREEN = new Color(50, 255, 126);

    private TreeNode highlightedNode = null;
    private boolean isTraversing = false;
    private Timer traversalTimer;
    private List traversalSequence;
    private int traversalIndex = 0;
    private String currentTraversalType = "";
    private int animationDelay = 500;

    private static final int NODE_RADIUS = 28;
    private static final int HORIZONTAL_SPACING = 45;
    private static final int VERTICAL_SPACING = 70;

    public BSTVisualizer() {
        super("K-POP DEMON HUNTER: SPIRIT CONTAINMENT GRID");
        bst = new BinarySearchTree();
        initializeUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 850);
        setLocationRelativeTo(null);
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));

        createTreePanel();
        createControlPanel();

        treeScrollPane = new JScrollPane(treePanel);
        treeScrollPane.setBorder(BorderFactory.createLineBorder(NEON_PINK, 2));
        treeScrollPane.getViewport().setBackground(DEEP_VOID);

        add(treeScrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.NORTH);
        add(createBottomPanel(), BorderLayout.SOUTH);

        getContentPane().setBackground(DEEP_VOID);
    }

    private void createTreePanel() {
        treePanel = new TreePanel();
        treePanel.setBackground(DEEP_VOID);
    }

    private void createControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(PANEL_DARK);
        controlPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        // Row 1: Hunter Actions
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 5));
        row1.setBackground(PANEL_DARK);

        JLabel inputLabel = new JLabel("DEMON ID:");
        inputLabel.setForeground(CYBER_CYAN);
        inputLabel.setFont(new Font("Monospaced", Font.BOLD, 14));

        inputField = new JTextField(8);
        inputField.setBackground(DEEP_VOID);
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(NEON_PINK);
        inputField.setBorder(BorderFactory.createLineBorder(CYBER_CYAN, 1));

        JButton insertBtn = createStyledButton("CAPTURE", SPIRIT_GREEN);
        JButton searchBtn = createStyledButton("SCAN GRID", CYBER_CYAN);
        JButton deleteBtn = createStyledButton("EXORCISE", NEON_PINK);
        JButton clearBtn = createStyledButton("WIPE GRID", Color.DARK_GRAY);

        row1.add(inputLabel);
        row1.add(inputField);
        row1.add(insertBtn);
        row1.add(searchBtn);
        row1.add(deleteBtn);
        row1.add(clearBtn);

        // Row 2: Sync (Traversals)
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 5));
        row2.setBackground(PANEL_DARK);

        JLabel syncLabel = new JLabel("SQUAD SYNC:");
        syncLabel.setForeground(NEON_PINK);
        syncLabel.setFont(new Font("Monospaced", Font.BOLD, 12));

        JButton inorderBtn = createStyledButton("Harmonic (In)", new Color(150, 50, 255));
        JButton preorderBtn = createStyledButton("Assault (Pre)", new Color(150, 50, 255));
        JButton postorderBtn = createStyledButton("Secure (Post)", new Color(150, 50, 255));

        row2.add(syncLabel);
        row2.add(inorderBtn);
        row2.add(preorderBtn);
        row2.add(postorderBtn);

        insertBtn.addActionListener(e -> insertValue());
        searchBtn.addActionListener(e -> searchValue());
        deleteBtn.addActionListener(e -> deleteValue());
        clearBtn.addActionListener(e -> clearGrid());
        inorderBtn.addActionListener(e -> showTraversal("Inorder"));
        preorderBtn.addActionListener(e -> showTraversal("Preorder"));
        postorderBtn.addActionListener(e -> showTraversal("Postorder"));

        controlPanel.add(row1);
        controlPanel.add(row2);
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(PANEL_DARK);

        outputArea = new JTextArea(5, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setBackground(DEEP_VOID);
        outputArea.setForeground(CYBER_CYAN);
        outputArea.setBorder(new EmptyBorder(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(CYBER_CYAN, 1));

        statusLabel = new JLabel("SYSTEM READY: HUNTER STANDING BY...");
        statusLabel.setForeground(SPIRIT_GREEN);
        statusLabel.setFont(new Font("Monospaced", Font.BOLD, 13));
        statusLabel.setBorder(new EmptyBorder(8, 15, 8, 15));

        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        return bottomPanel;
    }

    private JButton createStyledButton(String text, Color accent) {
        JButton btn = new JButton(text.toUpperCase());
        btn.setBackground(PANEL_DARK);
        btn.setForeground(accent);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(accent, 2));
        btn.setFont(new Font("Monospaced", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMargin(new Insets(5, 15, 5, 15));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(accent); btn.setForeground(DEEP_VOID); }
            public void mouseExited(MouseEvent e) { btn.setBackground(PANEL_DARK); btn.setForeground(accent); }
        });
        return btn;
    }

    // Business Logic with "Flavor" Text
    private void insertValue() {
        try {
            int val = Integer.parseInt(inputField.getText().trim());
            if (bst.search(val)) {
                appendOutput("![ALERT] Demon ID " + val + " is already contained.");
            } else {
                bst.insert(val);
                appendOutput(">> CAPTURED: Demon " + val + " locked in Containment Cell.");
                setStatus("ENTITY " + val + " SECURED", SPIRIT_GREEN);
            }
            inputField.setText("");
            updateTreeVisuals();
        } catch (Exception e) { setStatus("INPUT ERROR", Color.RED); }
    }

    private void searchValue() {
        try {
            int val = Integer.parseInt(inputField.getText().trim());
            if (bst.search(val)) {
                appendOutput(">> SCANNER: High energy signature found at Cell " + val);
                highlightNodeWithValue(val);
            } else {
                appendOutput(">> SCANNER: Cell " + val + " is vacant.");
            }
            updateTreeVisuals();
        } catch (Exception e) {}
    }

    private void deleteValue() {
        try {
            int val = Integer.parseInt(inputField.getText().trim());
            if (bst.search(val)) {
                bst.delete(val);
                appendOutput(">> EXORCISM: Entity " + val + " banished to the Void.");
                setStatus("EXORCISM COMPLETE", NEON_PINK);
            }
            updateTreeVisuals();
        } catch (Exception e) {}
    }

    private void clearGrid() {
        bst = new BinarySearchTree();
        outputArea.setText(">> SYSTEM WIPE: Containment Grid cleared.");
        updateTreeVisuals();
    }

    private void showTraversal(String type) {
        if (bst.isEmpty()) return;
        traversalSequence = new ArrayList<>();
        if (type.equals("Inorder")) collectInorderNodes(bst.getRoot(), traversalSequence);
        else if (type.equals("Preorder")) collectPreorderNodes(bst.getRoot(), traversalSequence);
        else collectPostorderNodes(bst.getRoot(), traversalSequence);

        appendOutput(">> SQUAD SYNC: Executing " + type + " pattern across grid...");
        startAnimation();
    }

    private void startAnimation() {
        isTraversing = true;
        traversalIndex = 0;
        traversalTimer = new Timer(animationDelay, e -> {
            if (traversalIndex < traversalSequence.size()) {
                highlightedNode = (TreeNode) traversalSequence.get(traversalIndex++);
                treePanel.repaint();
            } else {
                traversalTimer.stop();
                isTraversing = false;
                highlightedNode = null;
                appendOutput(">> SYNC COMPLETE.");
            }
        });
        traversalTimer.start();
    }

    private void updateTreeVisuals() {
        treePanel.revalidate();
        treePanel.repaint();
    }

    private void appendOutput(String text) {
        outputArea.append(text + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    private void setStatus(String text, Color color) {
        statusLabel.setText("STATUS: " + text);
        statusLabel.setForeground(color);
    }

    // Custom Styled Tree Panel
    private class TreePanel extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1200, 800);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (bst.isEmpty()) {
                g2d.setColor(NEON_PINK);
                g2d.setFont(new Font("Monospaced", Font.BOLD, 24));
                g2d.drawString("> NO DEMONS DETECTED IN GRID <", 380, 300);
            } else {
                drawTree(g2d, bst.getRoot(), getWidth() / 2, 60, getWidth() / 4);
            }
        }

        private void drawTree(Graphics2D g2d, TreeNode node, int x, int y, int hGap) {
            if (node == null) return;

            if (node.left != null) {
                g2d.setColor(CYBER_CYAN);
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawLine(x, y, x - hGap, y + VERTICAL_SPACING);
                drawTree(g2d, node.left, x - hGap, y + VERTICAL_SPACING, hGap / 2);
            }
            if (node.right != null) {
                g2d.setColor(CYBER_CYAN);
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawLine(x, y, x + hGap, y + VERTICAL_SPACING);
                drawTree(g2d, node.right, x + hGap, y + VERTICAL_SPACING, hGap / 2);
            }

            // Draw "Cell" (Node)
            boolean isHi = (node == highlightedNode);
            g2d.setColor(isHi ? NEON_PINK : DEEP_VOID);
            g2d.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

            g2d.setColor(isHi ? Color.WHITE : CYBER_CYAN);
            g2d.setStroke(new BasicStroke(isHi ? 4 : 2));
            g2d.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

            g2d.setFont(new Font("Monospaced", Font.BOLD, 16));
            String val = String.valueOf(node.data);
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(val, x - fm.stringWidth(val) / 2, y + fm.getAscent() / 2 - 2);
        }
    }

    // ============================================================
    // SQUAD SYNC HELPERS (Traversal Node Collection)
    // These collect the actual TreeNode objects for the animation
    // ============================================================

    private void collectInorderNodes(TreeNode node, List list) {
        if (node == null) return;
        collectInorderNodes(node.left, list);  // Left Sector
        list.add(node);                        // Sync Node
        collectInorderNodes(node.right, list); // Right Sector
    }

    private void collectPreorderNodes(TreeNode node, List list) {
        if (node == null) return;
        list.add(node);                        // Sync Node
        collectPreorderNodes(node.left, list);  // Left Sector
        collectPreorderNodes(node.right, list); // Right Sector
    }

    private void collectPostorderNodes(TreeNode node, List list) {
        if (node == null) return;
        collectPostorderNodes(node.left, list);  // Left Sector
        collectPostorderNodes(node.right, list); // Right Sector
        list.add(node);                          // Sync Node
    }

    /**
     * Scans the grid to find the specific TreeNode reference for highlighting.
     */
    private void highlightNodeWithValue(int value) {
        highlightedNode = findNodeRecursive(bst.getRoot(), value);
    }

    private TreeNode findNodeRecursive(TreeNode node, int value) {
        if (node == null) return null;
        if (node.data == value) return node;
        return (value < node.data)
                ? findNodeRecursive(node.left, value)
                : findNodeRecursive(node.right, value);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fallback to default if system theme fails
        }

        // Launch the UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            BSTVisualizer visualizer = new BSTVisualizer();
            visualizer.setVisible(true);
            visualizer.appendOutput(">> SYSTEM INITIALIZED...");
            visualizer.appendOutput(">> HUNTER ID: K-OPERATIVE_01");
            visualizer.appendOutput(">> STANDING BY FOR ENTITY CAPTURE.");
        });
    }
}
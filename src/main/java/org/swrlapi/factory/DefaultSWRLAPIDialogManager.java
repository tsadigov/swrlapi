package org.swrlapi.factory;

import checkers.nullness.quals.NonNull;
import checkers.nullness.quals.Nullable;
import org.swrlapi.ui.dialog.ExtensionFilter;
import org.swrlapi.ui.dialog.SWRLAPIDialogManager;
import org.swrlapi.ui.dialog.SWRLRuleEditorDialog;
import org.swrlapi.ui.model.SWRLRuleEngineModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DefaultSWRLAPIDialogManager implements SWRLAPIDialogManager
{
  @NonNull private final SWRLRuleEditorDialog swrlRuleEditorDialog;
  @Nullable private File lastDirectory = null;

  public DefaultSWRLAPIDialogManager(@NonNull SWRLRuleEngineModel swrlRuleEngineModel)
  {
    this.swrlRuleEditorDialog = new SWRLRuleEditorDialog(swrlRuleEngineModel, this);
  }

  @NonNull @Override public JDialog getSWRLRuleEditorDialog(@NonNull Component parent)
  {
    this.swrlRuleEditorDialog.setLocationRelativeTo(parent);
    return this.swrlRuleEditorDialog;
  }

  @NonNull @Override public JDialog getSWRLRuleEditorDialog(@NonNull Component parent, @NonNull String ruleName,
    @NonNull String ruleText, @NonNull String comment)
  {
    this.swrlRuleEditorDialog.setLocationRelativeTo(parent);
    this.swrlRuleEditorDialog.enableEditMode(ruleName, ruleText, comment);

    return this.swrlRuleEditorDialog;
  }

  @Override public int showConfirmCancelDialog(@NonNull Component parent, @NonNull String message,
    @NonNull String title)
  {
    return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_CANCEL_OPTION);
  }

  @Override public boolean showConfirmDialog(@NonNull Component parent, @NonNull String message, @NonNull String title)
  {
    return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
  }

  @Override public void showErrorMessageDialog(@NonNull Component parent, @NonNull String message,
    @NonNull String title)
  {
    JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
  }

  @Override public String showInputDialog(@NonNull Component parent, @NonNull String message,
    @NonNull String initialValue)
  {
    if (initialValue == null) {
      initialValue = "";
    }
    return JOptionPane.showInputDialog(parent, message, initialValue);
  }

  @Override public void showMessageDialog(@NonNull Component parent, @NonNull String message, @NonNull String title)
  {
    JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
  }

  @NonNull @Override public JFileChooser createFileChooser(@NonNull String title, @NonNull String fileDescription,
    @Nullable String fileExtension)
  {
    JFileChooser chooser = new JFileChooser(this.lastDirectory)
    {
      private static final long serialVersionUID = 1L;

      @Override public int showDialog(Component c, String s)
      {
        int rval = super.showDialog(c, s);
        if (rval == APPROVE_OPTION) {
          DefaultSWRLAPIDialogManager.this.lastDirectory = getCurrentDirectory();
        }
        return rval;
      }
    };
    chooser.setDialogTitle(title);

    if (fileExtension == null) {
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    } else if (fileExtension.length() > 0) {
      chooser.setFileFilter(new ExtensionFilter(fileExtension, fileDescription));
    }
    return chooser;
  }

  @NonNull @Override public JFileChooser createSaveFileChooser(@NonNull String title, @NonNull String fileDescription,
    @Nullable String fileExtension, final boolean overwrite)
  {
    JFileChooser chooser = new JFileChooser(this.lastDirectory)
    {
      private static final long serialVersionUID = 1L;

      @Override public int showDialog(Component c, String s)
      {
        int rval = super.showDialog(c, s);
        if (rval == APPROVE_OPTION) {
          DefaultSWRLAPIDialogManager.this.lastDirectory = getCurrentDirectory();
        }
        return rval;
      }

      @Override public void approveSelection()
      {
        if (!overwrite) {
          return;
        }

        File f = getSelectedFile();

        if (f.exists()) {
          String msg = "The file '" + f.getName() + "' already exists!\nDo you want to replace it?";
          String title = getDialogTitle();
          int option = JOptionPane
            .showConfirmDialog(this, msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
          if (option == JOptionPane.NO_OPTION) {
            return;
          }
        }
        super.approveSelection();
      }
    };

    chooser.setDialogType(JFileChooser.SAVE_DIALOG);
    chooser.setDialogTitle(title);
    if (fileExtension != null) {
      String text = fileDescription;
      chooser.setFileFilter(new ExtensionFilter(fileExtension, text));
    }
    return chooser;
  }
}

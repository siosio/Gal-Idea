package siosio.galgo;


import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementFactory
import com.intellij.util.IncorrectOperationException
import org.jetbrains.annotations.NotNull

public class Galgo extends PsiElementBaseIntentionAction implements IntentionAction {

  static def GalgoDictionary dictionary

  static {
    dictionary = new GalgoDictionary()
    dictionary.initialize()
  }

  @Override
  public boolean isAvailable(@NotNull Project project, Editor editor,
          @NotNull PsiElement element) {
    if (element == null || !element.isWritable()) {
      return false
    }
    if (element instanceof PsiComment) {
      return true
    }
    return false
  }

  @NotNull
  @Override
  public String getText() {
    "Replace Galgo"
  }

  @NotNull
  public String getFamilyName() {
    text
  }

  @Override
  public void invoke(@NotNull Project project, Editor editor,
          @NotNull PsiElement element) throws IncorrectOperationException {
    final PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();
    def galgo = factory.createCommentFromText(dictionary.replace(element.text), null)
    element.replace(galgo)
  }

  @Override
  public boolean startInWriteAction() {
    true
  }
}

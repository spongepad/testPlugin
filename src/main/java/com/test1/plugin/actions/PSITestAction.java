package com.test1.plugin.actions;

import com.intellij.lang.Language;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vcs.changes.ignore.psi.IgnoreFile;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.refactoring.Refactoring;
import com.intellij.refactoring.RefactoringFactory;
import com.twelvemonkeys.lang.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class PSITestAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final PsiFile file = e.getData(CommonDataKeys.PSI_FILE);
        final PsiElement element = e.getData(CommonDataKeys.PSI_ELEMENT);

        System.out.println("project : " + project);
        System.out.println("psiFIle: " + file +  " " + file.getClass());
        System.out.println("psiElement: " + element);
        System.out.println("ViewProvider : " + file.getViewProvider());
        System.out.println("Languages : " + file.getViewProvider().getLanguages());
        System.out.println("particular lang : " + file.getViewProvider().getPsi(Language.ANY));
        System.out.println("element : " + file.getViewProvider().findElementAt(1));

        var psicontent = file.getContainingFile();
        System.out.println("psiFIle: " + psicontent);

        String content = file.getText();
        Document document = file.getViewProvider().getDocument();
        System.out.println("content : " + content);
        System.out.println("doc : " + document);
        if(document != null) {
            Editor[] editors = EditorFactory.getInstance().getEditors(document);
            if(editors.length > 0) {
                String seletedText = editors[0].getSelectionModel().getSelectedText();
                if(!StringUtil.isEmpty(seletedText)) {
                    content = seletedText;
                }
            }
        }

/*        var refactoring = RefactoringFactory.getInstance(e.getProject()).createRename(element, "refactoringName");
        var usages = refactoring.findUsages();
        refactoring.doRefactoring(usages);
        refactoring.run();*/

        file.accept(new JavaRecursiveElementVisitor);

        var search = ReferencesSearch.search(element);
        System.out.println("search : " + search);

/*        Logger logger = LoggerFactory.getLogger(PSITestAction.class);
        VirtualFile[] vfs = ProjectRootManager.getInstance(e.getProject()).getContentSourceRoots();
        System.out.println(Arrays.toString(vfs));

        for (VirtualFile vf : vfs) {
            VirtualFile[] vfchild = vf.getChildren();
            System.out.println(vfchild.length);
            for (VirtualFile vfc : vfchild){
                Object File = PsiManager.getInstance(e.getProject()).findFile(vfc);
                System.out.println(File);
            }
        }*/
    }
}

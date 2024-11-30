package projectflowsyntax.intellijplugin
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

internal class SettingsPage : ColorSettingsPage {
    override fun getIcon(): Icon {
        return IconLoader.getIcon("/icons/projectFlowSyntaxIcon.svg", javaClass)
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return ProjectFlowSyntaxHighlighter()
    }

    @Suppress("LongMethod")
    override fun getDemoText(): String {
        return """
            # Project: Cloud Application Deployment
            
            # Define resources
            ${'$'}James(role:"Backend Developer")
            ${'$'}Carrie(role:"Frontend Developer")
            ${'$'}Juan(role:"DevOps Engineer")
            ${'$'}Yan(role:"Database Administrator")
            ${'$'}Misha(role:"Project Manager")
            
            # Main project structure
            @MainPhases: Init, DatabaseSetup, ServerDev, FrontendDev, Integration, Launch
            
            # Initialization phase
            @Init: ProjectKickoff, RequirementsGathering, ArchitectureDesign
            ProjectKickoff > RequirementsGathering > ArchitectureDesign
            ProjectKickoff > ${'$'}Misha
            RequirementsGathering > ${'$'}James, ${'$'}Carrie, ${'$'}Juan, ${'$'}Yan
            ArchitectureDesign > ${'$'}James, ${'$'}Carrie, ${'$'}Juan, ${'$'}Yan
            
            # Database setup stream
            @DatabaseSetup: ConfigureCloudInstance, SetupDatabase, LoadInitialData, DatabaseTesting
            ConfigureCloudInstance > SetupDatabase > LoadInitialData > DatabaseTesting
            ConfigureCloudInstance > ${'$'}Juan
            SetupDatabase > ${'$'}Yan
            LoadInitialData > ${'$'}Yan
            DatabaseTesting > ${'$'}Yan, ${'$'}James
            
            # Server development stream
            @ServerDev: DesignAPI, ImplementCore, ImplementAuth, ServerTesting
            DesignAPI > ImplementCore, ImplementAuth > ServerTesting
            DesignAPI > ${'$'}James
            ImplementCore > ${'$'}James
            ImplementAuth > ${'$'}James
            ServerTesting > ${'$'}James, ${'$'}Juan
            
            # Frontend development stream
            @FrontendDev: DesignUI, ImplementComponents, IntegrateAPI, FrontendTesting
            DesignUI > ImplementComponents > IntegrateAPI > FrontendTesting
            DesignUI > ${'$'}Carrie
            ImplementComponents > ${'$'}Carrie
            IntegrateAPI > ${'$'}Carrie
            FrontendTesting > ${'$'}Carrie, ${'$'}James
            
            # Integration and launch
            @Integration: SystemIntegration, PerformanceTesting
            @Launch: PreLaunchReview, Deployment, PostLaunchMonitoring
            
            SystemIntegration > PerformanceTesting > PreLaunchReview > Deployment > PostLaunchMonitoring
            SystemIntegration > ${'$'}James, ${'$'}Carrie, ${'$'}Juan, ${'$'}Yan
            PerformanceTesting > ${'$'}James, ${'$'}Carrie, ${'$'}Juan, ${'$'}Yan
            PreLaunchReview > ${'$'}Misha, ${'$'}James, ${'$'}Carrie, ${'$'}Juan, ${'$'}Yan
            Deployment > ${'$'}Juan
            PostLaunchMonitoring > ${'$'}Juan, ${'$'}Yan
            
            # Milestones
            %DatabaseReady(date:"2023-09-15")
            %ServerReady(date:"2023-10-01")
            %FrontendReady(date:"2023-10-01")
            %IntegrationComplete(date:"2023-10-15")
            %LaunchDay(date:"2023-11-01")
            
            DatabaseTesting > %DatabaseReady
            ServerTesting > %ServerReady
            FrontendTesting > %FrontendReady
            PerformanceTesting > %IntegrationComplete
            Deployment > %LaunchDay
            
            # Task attributes
            ProjectKickoff(duration:"1d")
            RequirementsGathering(duration:"3d")
            ArchitectureDesign(duration:"2d")
            ConfigureCloudInstance(duration:"1d")
            SetupDatabase(duration:"2d")
            LoadInitialData(duration:"1d")
            DatabaseTesting(duration:"2d")
            DesignAPI(duration:"2d")
            ImplementCore(duration:"5d")
            ImplementAuth(duration:"3d")
            ServerTesting(duration:"3d")
            DesignUI(duration:"2d")
            ImplementComponents(duration:"5d")
            IntegrateAPI(duration:"3d")
            FrontendTesting(duration:"3d")
            SystemIntegration(duration:"4d")
            PerformanceTesting(duration:"3d")
            PreLaunchReview(duration:"1d")
            Deployment(duration:"1d")
            PostLaunchMonitoring(duration:"5d")
            
            # Explode and Implode tasks
            Deployment ! 5
            ProjectKickoff, RequirementsGathering, ArchitectureDesign / InitX
            """.trimIndent()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? {
        return null
    }

    override fun getAttributeDescriptors(): Array<out AttributesDescriptor> {
        return Util.DESCRIPTORS
    }

    override fun getColorDescriptors(): Array<out ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    override fun getDisplayName(): String {
        return "ProjectFlowSyntax"
    }

    object Util {
        val DESCRIPTORS =
            arrayOf(
                AttributesDescriptor("Comment", ProjectFlowSyntaxHighlighterKeys.COMMENT),
                AttributesDescriptor("Key value separator :", ProjectFlowSyntaxHighlighterKeys.KV_SEPARATOR),
                AttributesDescriptor("Attribute", ProjectFlowSyntaxHighlighterKeys.ATTRIBUTE_KEY),
                AttributesDescriptor("Quoted text", ProjectFlowSyntaxHighlighterKeys.QUOTED_VALUE),
                AttributesDescriptor("Value", ProjectFlowSyntaxHighlighterKeys.VALUE),
                AttributesDescriptor("Identifier", ProjectFlowSyntaxHighlighterKeys.IDENTIFIER),
                AttributesDescriptor("Sigil for Cluster @", ProjectFlowSyntaxHighlighterKeys.CLUSTER_SIGIL),
                AttributesDescriptor("Sigil for Comment #", ProjectFlowSyntaxHighlighterKeys.COMMENT_SIGIL),
                AttributesDescriptor("Sigil for Milestone %", ProjectFlowSyntaxHighlighterKeys.MILESTONE_SIGIL),
                AttributesDescriptor("Sigil for NewTask *", ProjectFlowSyntaxHighlighterKeys.NEW_TASK_SIGIL),
                AttributesDescriptor("Sigil for Resource $", ProjectFlowSyntaxHighlighterKeys.RESOURCE_SIGIL),
                AttributesDescriptor("Operator for Negation ~", ProjectFlowSyntaxHighlighterKeys.NEGATION_OP),
                AttributesDescriptor("Operator for Explode !", ProjectFlowSyntaxHighlighterKeys.EXPLODE_OP),
                AttributesDescriptor("Operator for Implode ~!", ProjectFlowSyntaxHighlighterKeys.IMPLODE_OP),
                AttributesDescriptor("Operator for RequiredBy >", ProjectFlowSyntaxHighlighterKeys.REQUIRED_BY_OP),
                AttributesDescriptor("Separator ,", ProjectFlowSyntaxHighlighterKeys.SEPARATOR),
                AttributesDescriptor("Bracket left (", ProjectFlowSyntaxHighlighterKeys.LEFT_BRACKET),
                AttributesDescriptor("Bracket right )", ProjectFlowSyntaxHighlighterKeys.RIGHT_BRACKET),
                AttributesDescriptor("Double quotes \"", ProjectFlowSyntaxHighlighterKeys.DOUBLE_QUOTE),
                AttributesDescriptor("Number 0-9", ProjectFlowSyntaxHighlighterKeys.NUMBER),
                AttributesDescriptor("Whitespace", ProjectFlowSyntaxHighlighterKeys.WHITESPACE),
                AttributesDescriptor("Bad character", ProjectFlowSyntaxHighlighterKeys.BAD_CHARACTER),
            )
    }
}

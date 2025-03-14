grammar ProjectFlowSyntax

entry Project:
    lines+=Line*;

Line:
    EntityDefinition | Operation;

EntityDefinition:
    Task | Resource | Milestone | Cluster;

Task:
    name=ID (attributes=Attributes)?;

Resource:
    RESOURCE_PREFIX name=ID (attributes=Attributes)?;

Milestone:
    MILESTONE_PREFIX name=ID (attributes=Attributes)?;

Cluster:
    CLUSTER_PREFIX name=ID (':' items+=(TaskRef | MilestoneRef) (',' items+=(TaskRef | MilestoneRef))*)?;

Attributes:
    '(' attributeList+=Attribute (',' attributeList+=Attribute)* ')';

Attribute:
    name=ID ':' (value=STRING_VALUE | value=SIMPLE_VALUE | value=NUMBER | value=ID);

Operation:
    DependencyChain | Assignment | SplitTask |
    RemoveEntity | ExplodeTask | ImplodeTask;

DependencyChain:
    (leftMost+=(Task | Milestone) (',' leftMost+=(Task | Milestone))* segments+=DependencySegment+);

DependencySegment:
    (removal?='~')? '>' rightSide+=(Task | Milestone) (',' rightSide+=(Task | Milestone))*;

Assignment:
    (resources+=Resource (',' resources+=Resource)* (removal?='~')? '>' tasks+=Task (',' tasks+=Task)*) |
    (tasks+=Task (',' tasks+=Task)* (removal?='~')? '>' resources+=Resource (',' resources+=Resource)*);

SplitTask:
    (left?='*' '>' taskRef=TaskRef) | (taskRef=TaskRef '>' right?='*');

RemoveEntity:
    '~' entity=(TaskRef | ResourceRef | MilestoneRef | ClusterRef);

ExplodeTask:
    source=TaskRef '!' count=NUMBER;

ImplodeTask:
    sources+=TaskRef (',' sources+=TaskRef)* '/' target=TaskRef;

// Reference types
TaskRef: id=ID;
ResourceRef: RESOURCE_PREFIX id=ID;
MilestoneRef: MILESTONE_PREFIX id=ID;
ClusterRef: CLUSTER_PREFIX id=ID;

hidden terminal WS: /\s+/;
hidden terminal SL_COMMENT: /#[^\n\r]*/;
hidden terminal ML_COMMENT: /"""[\s\S]*?"""/;

terminal MILESTONE_PREFIX: /%/;
terminal RESOURCE_PREFIX: /\$/;
terminal CLUSTER_PREFIX: /@/;

terminal ID: /[_a-zA-Z][\w_]*/;
terminal NUMBER: /[1-9]+\d*/;
terminal STRING_VALUE: /"[^"]*"/;
terminal SIMPLE_VALUE: /[^,():>\s]+/;

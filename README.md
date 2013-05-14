tapestry-cdi
============

tapestry-cdi allows you to inject JSR 299 managed beans into tapestry services and components.

Using @Inject
=============

Contributes to InjectionProvider so that we can @Inject CDI beans into pages, components and tapestry services.

Injection by Constructor
========================

Contributes to ObjectProvider so that we can @Inject CDI beans into tapestry services contructors.

Qualifiers
==========

Handles cdi qualifiers. You can use qualifiers into pages, components and services

Helpers
=======

Add method helpers to ease the cdi bean instanciation 

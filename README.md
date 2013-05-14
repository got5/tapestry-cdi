tapestry-cdi
============

tapestry-cdi allows you to inject JSR 299 managed beans into tapestry services and components.

Using @Inject
=============

Contributes to InjectionProvider so that we can use @Inject (from tapestry 5 or javax.inject).

Injection by Constructor
========================

Contributes to ObjectProvider so that we can inject CDI beans into tapestry services.

Qualifiers
==========

Handles cdi qualifiers. You can use qualifiers into pages and component

Helpers
=======

Add method helpers to ease the cdi bean instanciation 

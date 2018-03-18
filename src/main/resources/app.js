/*
 * This file launches the application by asking Ext JS to create
 * and launch() the Application class.
 */
Ext.application({
    extend: 'licenta.Application',

    name: 'licenta',

    requires: [
        // This will automatically load all classes in the licenta namespace
        // so that application classes do not need to require each other.
        'licenta.*'
    ],

    // The name of the initial view to create.
    mainView: 'licenta.view.main.Main'
});

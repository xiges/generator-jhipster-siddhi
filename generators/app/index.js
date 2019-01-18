const util = require('util');
const chalk = require('chalk');
const generator = require('yeoman-generator');
const packagejs = require('../../package.json');
const semver = require('semver');
const BaseGenerator = require('generator-jhipster/generators/generator-base');
const jhipsterConstants = require('generator-jhipster/generators/generator-constants');
const jhipsterVar={moduleName:'siddhi'};
const JhipsterGenerator = generator.extend({});
util.inherits(JhipsterGenerator, BaseGenerator);

module.exports = JhipsterGenerator.extend({
    // eslint-disable-next-line object-shorthand
    constructor: function (...args) {
        generator.apply(this, args);

        // Microservice name
        this.argument('name', {
            type: String,
            required: true,
            desc: 'The name of the microservice (must not contain special characters or blank space)'
        });

        // Package name
        this.argument('package', {
            type: String,
            required: true,
            desc: 'Package name of the app (must be a valid java package name)'
        });
    },

    initializing: {
        readConfig() {
            this.jhipsterAppConfig = this.getJhipsterAppConfig();
            if (!this.jhipsterAppConfig) {
                this.log('Missing .yo-rc.json, the module will generate a default JHipter Company API microservice');
            }
        },
        displayLogo() {
            // Have Yeoman greet the user.
            this.log(`\nWelcome to the ${chalk.bold.yellow('JHipster company-api')} generator! ${chalk.yellow(`v${packagejs.version}\n`)}`);
        },
        checkJhipster() {
            if (!this.jhipsterAppConfig) {
                return;
            }
            const jhipsterVersion = this.jhipsterAppConfig.jhipsterVersion;
            const minimumJhipsterVersion = packagejs.dependencies['generator-jhipster'];
            if (!semver.satisfies(jhipsterVersion, minimumJhipsterVersion)) {
                this.warning(`\nYour generated project used an old JHipster version (${jhipsterVersion})... you need at least (${minimumJhipsterVersion})\n`);
            }
        }
    },

    prompting() {
        this.props = {};
    },

    writing() {
        // Generate default Company API microservice
        if (!this.jhipsterAppConfig) {
            // Set the values and copy the .yo-rc.json template
            this.jhipsterVersion = packagejs.dependencies['generator-jhipster'].version;
            this.baseName = this.options.name;
            this.packageName = this.options.package;
            this.packageFolder = this.packageName.replace(/\./g, '/');
            this.template('.yo-rc.json', '.yo-rc.json');

            // Invoke the main JHipster generator
            this.composeWith(require.resolve('generator-jhipster/generators/app'));

            // Get the JHipster configuration
            this.jhipsterAppConfig = this.fs.readJSON('.yo-rc.json')['generator-jhipster'];
            this.javaDir = `${jhipsterConstants.SERVER_MAIN_SRC_DIR + this.packageFolder}/`;

            // Copy a class
            this.template('MyConfiguration.java', `${this.javaDir}/config/MyConfiguration.java`);
// Copy a resource file
            this.template('application-qa.yml', `${jhipsterConstants.SERVER_MAIN_RES_DIR}/config/application-qa.yml`);

            // Add templates here
        }
    },

    install() {

        this.addMavenDependency('org.wso2.siddhi', 'siddhi-query-api', '4.1.7');
        this.addMavenDependency('org.wso2.siddhi', 'siddhi-query-compiler', '4.1.7');
        this.addMavenDependency('org.wso2.siddhi', 'siddhi-annotations', '4.1.7');
        this.addMavenDependency('org.wso2.siddhi', 'siddhi-core', '4.1.7');

    },

    end() {
        this.log('End of company-api generator');
    }
});

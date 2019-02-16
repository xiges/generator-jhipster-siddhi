const chalk = require('chalk');
const generator = require('yeoman-generator');
const packagejs = require('../../package.json');

// Stores JHipster variables
const jhipsterVar = { moduleName: 'siddhiX' };

// Stores JHipster functions
const jhipsterFunc = {};

module.exports = generator.extend({

    initializing: {
        compose() {
            this.composeWith('jhipster:modules',
                { jhipsterVar, jhipsterFunc },
                this.options.testmode ? { local: require.resolve('generator-jhipster/generators/modules') } : null
            );
        },
        displayLogo() {
            // Have Yeoman greet the user.
            this.log(`Welcome to the ${chalk.bold.yellow('JHipster siddhi')} generator! ${chalk.yellow(`v${packagejs.version}\n`)}`);
        }
    },

    prompting() {
        // return the function to call once the task is done
        const done = this.async();


        const prompts = [
            {
                type: 'input',
                name: 'userFortune',
                message: 'Please write your own fortune cookie',
                default: 'Do. Or do not. There is no try.'
            }
        ];

        this.prompt(prompts).then((props) => {
            this.props = props;
            // To access props later use this.props.someOption;

            done();
        });
    },

    writing() {
        // function to use directly template
        this.template = function (source, destination) {
            this.fs.copyTpl(
                this.templatePath(source),
                this.destinationPath(destination),
                this
            );
        };

        this.baseName = jhipsterVar.baseName;
        this.packageName = jhipsterVar.packageName;
        this.packageFolder = jhipsterVar.packageFolder;
        this.angularAppName = jhipsterVar.angularAppName;
        this.clientFramework = jhipsterVar.clientFramework;
        this.clientPackageManager = jhipsterVar.clientPackageManager;
        const javaDir = jhipsterVar.javaDir;
        const resourceDir = jhipsterVar.resourceDir;
        const webappDir = jhipsterVar.webappDir;



        this.template('src/main/java/package/domain/_RealtimeAnalytics.java', `${javaDir}domain/RealtimeAnalytics.java`);
        this.template('src/main/java/package/domain/_RealtimeAnalyticsService.java', `${javaDir}domain/RealtimeAnalyticsService.java`);
        this.template('src/main/java/package/domain/_StockData.java', `${javaDir}domain/StockData.java`);

        this.template('src/main/java/package/web/rest/_StockDataResource.java', `${javaDir}web/rest/StockDataResource.java`);
    },

    install() {
        let logMsg =
            `To install your dependencies manually, run: ${chalk.yellow.bold(`${this.clientPackageManager} install`)}`;

        if (this.clientFramework === 'angular1') {
            logMsg =
                `To install your dependencies manually, run: ${chalk.yellow.bold(`${this.clientPackageManager} install & bower install`)}`;
        }
        const injectDependenciesAndConstants = (err) => {
            if (err) {
                this.warning('Install of dependencies failed!');
                this.log(logMsg);
            } else if (this.clientFramework === 'angular1') {
                this.spawnCommand('gulp', ['install']);
            }
        };
        const installConfig = {
            bower: this.clientFramework === 'angular1',
            npm: this.clientPackageManager !== 'yarn',
            yarn: this.clientPackageManager === 'yarn',
            callback: injectDependenciesAndConstants
        };
        this.installDependencies(installConfig);


        this.addMavenDependency('org.wso2.siddhi', 'siddhi-query-api', '4.1.7');
        this.addMavenDependency('org.wso2.siddhi', 'siddhi-query-compiler', '4.1.7');
        this.addMavenDependency('org.wso2.siddhi', 'siddhi-annotations', '4.1.7');
        this.addMavenDependency('org.wso2.siddhi', 'siddhi-core', '4.1.7');
    },

    end() {
        this.log('End of Siddhi generator');
    }
});
